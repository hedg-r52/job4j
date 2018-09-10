package ru.job4j.jdbc.tracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tracker implements AutoCloseable {
    private final List<Item> items = new ArrayList<>();
    private int position = 0;
    private static final Random RN = new Random();
    private Connection connection;

    /**
     * Constructor
     * set connection to database
     * check tables and create them if absent
     *
     * @param config name of file of properties
     * @throws SQLException
     */
    public Tracker(String config) throws SQLException {
        Settings settings = new Settings();
        settings.load(config);
        connection = DriverManager.getConnection(
                settings.getValue("db.url"),
                settings.getValue("db.user"),
                settings.getValue("db.pass")
        );
        checkTablesAndCreateIfAbsent();
    }

    /**
     * Добавление заявки
     *
     * @param item новая заявка
     * @return ссылка на созданную заявку
     */
    public Item add(Item item) {
        Item result = null;
        try (PreparedStatement st = connection.prepareStatement("insert into items(name, \"desc\", created) values (?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.setLong(3, item.getCreated());
            int id = st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                item.setId(rs.getInt(1));
                result = item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Редактирование заявки (замена заявки на новую)
     *
     * @param id      Уникальный ключ заявки
     * @param newItem новая заявка
     */
    public void replace(int id, Item newItem) {
        try (PreparedStatement st = connection.prepareStatement("update items set name=?, \"desc\"=?, created=? where id=?;")) {
            st.setString(1, newItem.getName());
            st.setString(2, newItem.getDesc());
            st.setLong(3, newItem.getCreated());
            st.setInt(4, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Удаление заявки и сдвиг остальных элементов
     *
     * @param id Уникальный ключ заявки
     */
    public void delete(int id) {
        try (PreparedStatement st = connection.prepareStatement("delete from items where id = ?")) {
            st.setInt(1, id);
            int result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получить список всех заявок
     *
     * @return массив заявок
     */
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("select * from items;")) {
            while (rs.next()) {
                result.add(
                        new Item(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("desc"),
                                rs.getLong("created")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получить список заявок по имени
     *
     * @param key Имя для поиска
     * @return массив заявок
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("select * from items where name like ? escape '!';")) {
            st.setString(1, "%" + key + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    result.add(
                            new Item(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("desc"),
                                    rs.getLong("created")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получить заявку по id
     *
     * @param id Уникальный идентификатор
     * @return Заявка
     */
    public Item findById(int id) {
        Item result = null;
        try (PreparedStatement st = connection.prepareStatement("select * from items where id = ?;")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    result = new Item(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("desc"),
                            rs.getLong("created")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }

    private void checkTablesAndCreateIfAbsent() throws SQLException {
        if (!isTableExist("items")) {
            try (Statement st = connection.createStatement()) {
                int result = st.executeUpdate(getQuery("create_items.sql"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getQuery(String filename) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(filename).getFile()))) {
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean isTableExist(String tableName) {
        boolean result = false;
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet rs = dbm.getTables(null, null, tableName, null);
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}