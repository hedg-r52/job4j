package crud.persistent;

import crud.model.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.utils.jdbc.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBGeo {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DBGeo INSTANCE = new DBGeo();
    private static final Logger LOGGER = LogManager.getLogger(DBGeo.class);

    /**
     * Constructor
     */
    public DBGeo() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost:5432/tracker");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("postgres");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DBGeo getInstance() {
        return INSTANCE;
    }

    /**
     * Get list of countries
     * @return list of countries
     */
    public List<String> countries() {
        List<String> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            db.query("select title from countries;",
                    Arrays.asList(),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                result.add(rs.getString(1));
                            }
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Get list of cities
     * @param country title of country
     * @return list of cities
     */
    public List<String> cities(String country) {
        List<String> result = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             DBHelper db = new DBHelper(connection, LOGGER)) {
            db.query("select title from cities where countries_id in (select id from countries where title = ?);",
                    Arrays.asList(country),
                    ps -> {
                        try (final ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                result.add(rs.getString(1));
                            }
                        }
                    });
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }
}
