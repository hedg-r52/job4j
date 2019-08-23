package persistent;

import model.Seat;
import model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.utils.jdbc.DBHelper;

/**
 * DB Store
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.08.2019
 */
public class DBStore implements Store {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBStore INSTANCE = new DBStore();
    private static final Logger LOGGER = LogManager.getLogger(DBStore.class);

    /**
     * Constructor
     */
    public DBStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost:5432/cinema");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("postgres");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    /**
     * Get instance
     *
     * @return instance of DBStore
     */
    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public Seat seat(Seat seat) {
        Seat result = null;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            result = db.query(
                    "select row, place, sold, price from halls where row = ? and place = ?;",
                    Arrays.asList(seat.getRow(), seat.getPlace()),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            Seat res = null;
                            if (rs.next()) {
                                res = new Seat(
                                        rs.getInt("row"),
                                        rs.getInt("place"),
                                        rs.getInt("price"),
                                        rs.getBoolean("sold")
                                );
                            }
                            return res;
                        }
                    }).orElse(null);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Seat> seats() {
        final List<Seat> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            db.query(
                    "select \"row\", place, sold, price from halls order by \"row\", place",
                    Arrays.asList(),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                result.add(new Seat(
                                        rs.getInt("row"),
                                        rs.getInt("place"),
                                        rs.getInt("price"),
                                        rs.getBoolean("sold")
                                ));
                            }
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean executePay(Seat seat, User user) {
        boolean result = false;
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            connection.setAutoCommit(false);
            boolean doRollback = false;
            int id = db.query(
                    "select id from halls where \"row\"=? and place=? and not sold",
                    Arrays.asList(seat.getRow(), seat.getPlace()),
                    ps -> {
                        int seatId = -1;
                        try (final ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                seatId = rs.getInt("id");
                            }
                        }
                        return seatId;
                    }
            ).orElse(-1);
            if (id != -1) {
                result = db.query(
                        "update halls set sold = ? where id = ?;",
                        Arrays.asList(true, id),
                        ps -> {
                            return ps.executeUpdate() == 1;
                        }
                ).orElse(false);
                if (result) {
                    db.query(
                            "insert into accounts (username, phone, halls_id) values (?, ?, ?)",
                            Arrays.asList(user.getName(), user.getPhone(), id),
                            ps -> { }
                    );
                    connection.commit();
                }
                doRollback = !result;
            } else {
                doRollback = true;
            }
            if (doRollback) {
                connection.rollback();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }
}
