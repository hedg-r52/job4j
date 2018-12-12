package ru.job4j.jdbc.tracker;

import java.sql.*;
import java.util.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TrackerSQL implements AutoCloseable {
    private final Connection connection;
    private final Map<Class<?>, TripleConEx<Integer, PreparedStatement, Object>> dispatch = new HashMap();

    public TrackerSQL(Connection connection) {
        this.connection = connection;
        this.dispatch.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispatch.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.dispatch.put(Long.class, (index, ps, value) -> ps.setLong(index, (Long) value));
    }

    private <T> void forIndex(List<T> list, BiConEx<Integer, T> consumer) throws Exception {
        for (int index = 0; index != list.size(); index++) {
            consumer.accept(index, list.get(index));
        }
    }

    private <R> Optional<R> db(String sql, List<Object> params, FunEx<PreparedStatement, R> fun, int key) {
        Optional<R> result = Optional.empty();
        try (PreparedStatement pr = connection.prepareStatement(sql, key)) {
            this.forIndex(
                    params,
                    (index, value) -> {
                        dispatch.get(value.getClass()).accept(index + 1, pr, value);
                    }
            );
            result = Optional.of(fun.apply(pr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private <R> Optional<R> db(String sql, List<Object> params, FunEx<PreparedStatement, R> fun) {
        return this.db(sql, params, fun, Statement.NO_GENERATED_KEYS);
    }

    private <R> void db(String sql, List<Object> params, ConEx<PreparedStatement> fun, int key) {
        this.db(
                sql, params,
                ps -> {
                    fun.accept(ps);
                    return Optional.empty();
                }, key
        );
    }

    private <R> void db(String sql, List<Object> params, ConEx<PreparedStatement> fun) {
        this.db(sql, params, fun, Statement.NO_GENERATED_KEYS);
    }

    /**
     * Добавление заявки
     * @param item новая заявка
     * @return ссылка на созданную заявку
     */
    public Item add(Item item) {
        this.db(
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
        this.db(
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
        this.db(
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
        this.db(
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
        return this.db(
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
        return this.db(
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

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
