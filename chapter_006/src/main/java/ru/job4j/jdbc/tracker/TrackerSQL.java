package ru.job4j.jdbc.tracker;

import ru.job4j.utils.jdbc.DBHelper;
import org.apache.log4j.LogManager;
import java.sql.*;
import java.util.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TrackerSQL implements AutoCloseable {
    private final DBHelper db;

    public TrackerSQL(Connection connection) {
        this.db = new DBHelper(connection, LogManager.getLogger(TrackerSQL.class));
    }

    /**
     * Добавление заявки
     * @param item новая заявка
     * @return ссылка на созданную заявку
     */
    public Item add(Item item) {
        db.query(
                "insert into items (name, \"desc\") values (?, ?)",
                Arrays.asList(item.getName(), item.getDesc()),
                ps -> {
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            item.setId(generatedKeys.getInt(1));
                        }
                    }
                },
                Statement.RETURN_GENERATED_KEYS
        );
        return item;
    }

    /**
     * Редактирование заявки (замена заявки на новую)
     * @param id      Уникальный ключ заявки
     * @param newItem новая заявка
     */
    public void replace(int id, Item newItem) {
        db.query(
                "update items set name=?, \"desc\"=? where id=?",
                Arrays.asList(newItem.getName(), newItem.getDesc(), id),
                ps -> {
                    ps.executeUpdate();
                }
        );
    }

    /**
     * Удаление заявки и сдвиг остальных элементов
     * @param id Уникальный ключ заявки
     */
    public void delete(int id) {
        db.query(
                "delete from items where id = ?",
                Arrays.asList(id),
                ps -> {
                    ps.executeUpdate();
                }
        );
    }

    /**
     * Получить список всех заявок
     *
     * @return массив заявок
     */
    public List<Item> findAll() {
        final List<Item> result = new ArrayList<>();
        db.query(
                "select * from items", Arrays.asList(),
                ps -> {
                    try (final ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            result.add(new Item(rs.getInt("id"), rs.getString("name"), rs.getString("desc"), rs.getLong("created")));
                        }
                    }
                }
        );
        return result;
    }

    /**
     * Получить список заявок по имени
     *
     * @param key Имя для поиска
     * @return массив заявок
     */
    public List<Item> findByName(String key) {
        return db.query(
                "select * from items where name like ? escape '!'",
                Arrays.asList("%" + key + "%"),
                ps -> {
                    List<Item> result = new ArrayList<>();
                    try (ResultSet rs = ps.executeQuery()) {
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
                    return result;
                }
        ).orElse(new ArrayList<>());
    }

    /**
     * Получить заявку по id
     *
     * @param id Уникальный идентификатор
     * @return Заявка
     */
    public Item findById(int id) {
        return db.query(
                "select * from items where id = ?",
                Arrays.asList(id),
                ps -> {
                    Item result = null;
                    try (final ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            result = new Item(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("desc"),
                                    rs.getLong("created")
                            );
                        }
                    }
                    return result;
                }
        ).orElse(null);
    }

    /**
     * Закрытие
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        db.close();
    }
}
