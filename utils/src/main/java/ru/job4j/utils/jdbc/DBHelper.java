package ru.job4j.utils.jdbc;

import java.sql.*;
import java.util.*;
import org.apache.log4j.*;
import ru.job4j.utils.functional.*;

public class DBHelper implements AutoCloseable {
    private final Connection connection;
    private final Logger logger;
    private final Map<Class<?>, TripleConEx<Integer, PreparedStatement, Object>> dispatch;

    public DBHelper(Connection connection, Logger logger) {
        this.connection = connection;
        this.logger = logger;
        this.dispatch = new HashMap<>();
        this.dispatch.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispatch.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.dispatch.put(Long.class, (index, ps, value) -> ps.setLong(index, (Long) value));
        this.dispatch.put(Timestamp.class, (index, ps, value) -> ps.setTimestamp(index, (Timestamp) value));
        this.dispatch.put(Boolean.class, (index, ps, value) -> ps.setBoolean(index, (Boolean) value));
    }

    public  <T> void forIndex(List<T> list, BiConEx<Integer, T> consumer) throws Exception {
        for (int index = 0; index != list.size(); index++) {
            consumer.accept(index, list.get(index));
        }
    }

    public  <R> Optional<R> query(String sql, List<Object> params, FunEx<PreparedStatement, R> fun, int key) {
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
            logger.warn(e.toString());
        }
        return result;
    }

    public  <R> Optional<R> query(String sql, List<Object> params, FunEx<PreparedStatement, R> fun) {
        return this.query(sql, params, fun, Statement.NO_GENERATED_KEYS);
    }

    public  void query(String sql, List<Object> params, ConEx<PreparedStatement> fun, int key) {
        this.query(
                sql, params,
                ps -> {
                    fun.accept(ps);
                    return Optional.empty();
                }, key
        );
    }

    public  void query(String sql, List<Object> params, ConEx<PreparedStatement> fun) {
        this.query(sql, params, fun, Statement.NO_GENERATED_KEYS);
    }

    public  void queryCycle(String sql, List<Object> params, ConEx<PreparedStatement> fun, int key) {
        try {
            connection.setAutoCommit(false);
            for (Object p : params) {
                this.query(
                        sql, (List<Object>) p,
                        ps -> {
                            fun.accept(ps);
                            return Optional.empty();
                        }, key
                );
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.warn(e.toString());
        }
    }

    public  void queryCycle(String sql, List<Object> params, ConEx<PreparedStatement> fun) {
        this.queryCycle(sql, params, fun, Statement.NO_GENERATED_KEYS);
    }

    @Override
    public void close() throws Exception {
        if (!connection.isClosed()) {
            connection.close();
        }
    }
}
