package ru.job4j.jdbc.magnit;

import java.sql.*;

public class StoreSQL {
    private Connection connection;

    /**
     * constructor
     *
     * @param config - object containing settings for connecting to db
     */
    public StoreSQL(Config config) {
        try {
            connection = DriverManager.getConnection(String.format("%s:%s", config.getValue("db.url"), config.getValue("db.file")));
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkTablesAndCreateIfAbsent();
    }

    /**
     * Method checks that tables is exist, and create if tables is absent
     */
    private void checkTablesAndCreateIfAbsent() {
        if (!isTableExist("entry")) {
            try (Statement st = connection.createStatement()) {
                int result = st.executeUpdate("create table entry(field integer);");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generate n-database records
     *
     * @param n count of generated records
     */
    public void generate(int n) {
        if (!isTableEntryEmpty()) {
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("delete from entry;");
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < n; i++) {
            int value = (int) (1000 * n * Math.random());
            try (PreparedStatement st = connection.prepareStatement("insert into entry values (?)")) {
                st.setInt(1, value);
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableEntryEmpty() {
        boolean result = true;
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("select count(*) as recordsCount from entry;")) {
            if (rs.next()) {
                result = (rs.getInt(1) == 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Entries selectAll() {
        Entries result = new Entries();
        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery("select * from entry;")) {
            while (rs.next()) {
                result.add(new Entry(rs.getInt("field")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Method checks that tables is exist
     *
     * @param tableName name of table
     * @return true if table exist
     */
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
