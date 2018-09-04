package ru.job4j.jdbc.magnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreSQL {
    private Connection connection;

    /**
     * constructor
     *
     * @param config - object containing settings for connecting to db
     * @throws SQLException
     */
    public StoreSQL(Config config) throws SQLException {
        connection = DriverManager.getConnection(String.format("%s:%s", config.getValue("db.url"), config.getValue("db.file")));
        connection.setAutoCommit(false);
        checkTablesAndCreateIfAbsent();
    }

    /**
     * Method checks that tables is exist, and create if tables is absent
     *
     * @throws SQLException
     */
    private void checkTablesAndCreateIfAbsent() throws SQLException {
        if (!isTableExist("entry")) {
            Statement st = connection.createStatement();
            int result = st.executeUpdate("create table entry(field integer);");
            connection.commit();
        }
    }

    /**
     * Generate n-database records
     *
     * @param n count of generated records
     * @throws SQLException
     */
    public void generate(int n) throws SQLException {
        if (!isTableEntryEmpty()) {
            Statement st = connection.createStatement();
            st.executeUpdate("delete from entry;");
            connection.commit();
        }

        for (int i = 0; i < n; i++) {
            int value = (int) (1000 * n * Math.random());
            PreparedStatement st = connection.prepareStatement(
                    "insert into entry values (?)"
            );
            st.setInt(1, value);
            st.executeUpdate();
        }
        connection.commit();
    }

    private boolean isTableEntryEmpty() throws SQLException {
        boolean result = true;
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select count(*) as recordsCount from entry;");
        if (rs.next()) {
            result = (rs.getInt(1) == 0);
        }
        return result;
    }

    public Entries selectAll() throws SQLException {
        Entries result = new Entries();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("select * from entry;");
        while (rs.next()) {
            result.add(new Entry(rs.getInt("field")));
        }
        return result;
    }

    /**
     * Method checks that tables is exist
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
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
