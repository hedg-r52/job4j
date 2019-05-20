package crud.persistent;

import crud.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.utils.jdbc.DBHelper;

/**
 * DBStore
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DBStore implements Store<User> {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBStore INSTANCE = new DBStore();
    private static final Logger LOGGER = LogManager.getLogger(DBStore.class);

    /**
     * Constructor
     */
    public DBStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost:5432/tracker");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("postgres");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        this.createTableUsersIfNotExist();
    }

    /**
     * Get instance
     * @return instance of DBStore
     */
    public static DBStore getInstance() {
        return INSTANCE;
    }

    /**
     * Add new user to store
     * @param user new user
     * @return result of execution: true - executed or false - not
     */
    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            result = db.query(
                    "insert into users (name, login, email) values (?, ?, ?);",
                    Arrays.asList(user.name(), user.login(), user.email()),
                    ps -> {
                        ps.executeUpdate();
                        return true;
                    }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update user
     * @param index id of user which can be updated
     * @param user new user
     * @return result of execution: true - executed or false - not
     */
    @Override
    public boolean update(int index, User user) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            result = db.query(
                    "update users set name = ?, login = ?, email = ? where id = ?;",
                    Arrays.asList(user.name(), user.login(), user.email(), index),
                    ps -> {
                        ps.executeUpdate();
                        return true;
                    }
            ).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Delete user by id
     * @param id id of user which can be deleted
     * @return result of execution: true - executed or false - not
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            result = db.query(
                    "delete from users where id = ?;",
                    Arrays.asList(id),
                    ps -> {
                        ps.executeUpdate();
                        return true;
                    }
            ).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get list of all users
     * @return list of users
     */
    @Override
    public List<User> findAll() {
        final List<User> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            db.query("select * from users order by id;",
                Arrays.asList(),
                ps -> {
                    try (final ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            result.add(
                                    new User(
                                            rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getString("login"),
                                            rs.getString("email")
                                            )
                            );
                        }
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Find user by Id
     * @param id id
     * @return user represented by Optional or Optional.empty()
     */
    @Override
    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            user = db.query(
                    "select * from users where id = ?",
                    Arrays.asList(id),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            User result = null;
                            if (rs.next()) {
                                result = new User(
                                        rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("login"),
                                        rs.getString("email")
                                );
                            }
                            return result;
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Method create if tables is absent
     */
    private void createTableUsersIfNotExist() {
        try (Connection connection = SOURCE.getConnection();
                DBHelper db = new DBHelper(connection, LOGGER)) {
            db.query("create table if not exists users("
                        + "id serial primary key,"
                        + "name varchar(50),"
                        + "login varchar(20),"
                        + "email varchar(50)"
                        + ");",
                    Arrays.asList(),
                    ps -> {
                        ps.execute();
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
