package crud.persistent;

import crud.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.utils.jdbc.DBHelper;

import javax.swing.text.html.Option;

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
    private final RoleStore roles = MemoryRoleStore.getInstance();
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
            Optional<Integer> countryId = countryIdByName(db, user.getCountry());
            Optional<Integer> cityId = cityIdByName(db, user.getCity());
            if (cityId.isPresent() && countryId.isPresent()) {
                result = db.query(
                        "insert into users (name, login, email, password, role, created, country, city) values (?, ?, ?, ?, ?, now(), ?, ?);",
                        Arrays.asList(
                                user.getName(),
                                user.getLogin(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getRole(),
                                countryId.get(),
                                cityId.get()
                        ),
                        ps -> {
                            ps.executeUpdate();
                            return true;
                        }).orElse(false);
            }
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
            Optional<Integer> countryId = countryIdByName(db, user.getCountry());
            Optional<Integer> cityId = cityIdByName(db, user.getCity());
            if (countryId.isPresent() && cityId.isPresent()) {
                result = db.query(
                        "update users set name = ?, login = ?, email = ?, role = ?, password = ?, country = ?, city = ? where id = ?;",
                        Arrays.asList(
                                user.getName(),
                                user.getLogin(),
                                user.getEmail(),
                                user.getRole(),
                                user.getPassword(),
                                countryId.get(),
                                cityId.get(),
                                index
                        ),
                        ps -> {
                            ps.executeUpdate();
                            return true;
                        }
                ).orElse(false);
            }
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
            db.query("select u.id, u.name, u.login, u.email, u.password, u.role, co.title country, ci.title city"
                            + " from users u"
                            + " left join countries co on u.country = co.id"
                            + " left join cities ci on u.city = ci.id"
                            + " order by id;",
                Arrays.asList(),
                ps -> {
                    try (final ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            User user = new User(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("login"),
                                    rs.getString("email"),
                                    rs.getString("password"),
                                    rs.getString("role")
                            );
                            user.setCountry(rs.getString("country"));
                            user.setCity(rs.getString("city"));
                            result.add(user);
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
                        "select u.id, u.name, u.login, u.email, u.password, u.role, co.title country, ci.title city"
                            + " from users u"
                            + " left join countries co on u.country = co.id"
                            + " left join cities ci on u.city = ci.id"
                            + " where u.id = ?;",
                    Arrays.asList(id),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            User result = null;
                            if (rs.next()) {
                                result = new User(
                                        rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("login"),
                                        rs.getString("email"),
                                        rs.getString("password"),
                                        rs.getString("role")
                                );
                                result.setCountry(rs.getString("country"));
                                result.setCity(rs.getString("city"));
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

    @Override
    public boolean isCredential(String login, String password) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            result = db.query(
                    "select * from users where login=? and password=?;",
                    Arrays.asList(login, password),
                        ps -> {
                            try (final ResultSet rs = ps.executeQuery()) {
                                return rs.next();
                            }
                        }
            ).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> user = Optional.empty();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            user = db.query(
                        "select u.id, u.name, u.login, u.email, u.password, u.role, co.title country, ci.title city"
                            + " from users u"
                            + " left join countries co on u.country = co.id"
                            + " left join cities ci on u.city = ci.id"
                            + " where u.login = ?;",
                    Arrays.asList(login),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            User result = null;
                            if (rs.next()) {
                                result = new User(
                                        rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("login"),
                                        rs.getString("email"),
                                        rs.getString("password"),
                                        rs.getString("role")
                                );
                                result.setCountry(rs.getString("country"));
                                result.setCity(rs.getString("city"));
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
     * Get country id by name
     * @param db DBHelper for sql connection
     * @param countryTitle title
     * @return return Optional<Integer> value: value if found, else - Optional.empty()
     */
    private Optional<Integer> countryIdByName(DBHelper db, String countryTitle) {
        return db.query(
                "select id from countries where title = ?;",
                Arrays.asList(countryTitle),
                ps -> {
                    Optional<Integer> id = Optional.empty();
                    try (final ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            id = Optional.of(rs.getInt("id"));
                        }
                    }
                    return id;
                }).orElse(Optional.empty());
    }

    /**
     * Get city id by name
     * @param db DBHelper for sql connection
     * @param cityTitle title
     * @return return Optional<Integer> value: value if found, else - Optional.empty()
     */
    private Optional<Integer> cityIdByName(DBHelper db, String cityTitle) {
        return db.query(
                "select id from cities where title = ?;",
                Arrays.asList(cityTitle),
                ps -> {
                    Optional<Integer> id = Optional.empty();
                    try (final ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            id = Optional.of(rs.getInt("id"));
                        }
                    }
                    return id;
                }).orElse(Optional.empty());
    }
}
