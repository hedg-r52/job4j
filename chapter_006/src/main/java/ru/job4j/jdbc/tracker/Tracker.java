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
    public Item add(Item item) throws SQLException {
        PreparedStatement st = connection.prepareStatement("insert into items(name, \"desc\", created) values (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        st.setString(1, item.getName());
        st.setString(2, item.getDesc());
        st.setLong(3, item.getCreated());
        int id = st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        item.setId(rs.getInt(1));
        return item;
    }

    /**
     * Редактирование заявки (замена заявки на новую)
     *
     * @param id      Уникальный ключ заявки
     * @param newItem новая заявка
     */
    public void replace(int id, Item newItem) throws SQLException {
        PreparedStatement st = connection.prepareStatement("update items set name=?, \"desc\"=?, created=? where id=?;");
        st.setString(1, newItem.getName());
        st.setString(2, newItem.getDesc());
        st.setLong(3, newItem.getCreated());
        st.setInt(4, id);
        st.executeUpdate();
    }



    /**
     * Удаление заявки и сдвиг остальных элементов
     *
     * @param id Уникальный ключ заявки
     */
    public void delete(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement("delete from items where id = ?");
        st.setInt(1, id);
        int result = st.executeUpdate();
    }

    /**
     * Получить список всех заявок
     *
     * @return массив заявок
     */
    public List<Item> findAll() throws SQLException {
        List<Item> result = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from items;");
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
        return result;
    }

    /**
     * Получить список заявок по имени
     *
     * @param key Имя для поиска
     * @return массив заявок
     */
    public List<Item> findByName(String key) throws SQLException {
        List<Item> result = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("select * from items where name like ? escape '!';");
        st.setString(1, "%" + key + "%");
        ResultSet rs = st.executeQuery();
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
        return result;
    }

    /**
     * Получить заявку по id
     *
     * @param id Уникальный идентификатор
     * @return Заявка
     */
    public Item findById(int id) throws SQLException {
        Item result = null;
        PreparedStatement st = connection.prepareStatement("select * from items where id = ?;");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            result = new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("desc"),
                    rs.getLong("created")
            );
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
            Statement st = connection.createStatement();
            try {
                int result = st.executeUpdate(getQuery("create_items.sql"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getQuery(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(filename).getFile()))) {
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    private boolean isTableExist(String tableName) throws SQLException {
        boolean result = false;
        DatabaseMetaData dbm = connection.getMetaData();
        try (ResultSet rs = dbm.getTables(null, null, tableName, null)) {
            result = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}