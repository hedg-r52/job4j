package ru.job4j.jdbc.tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {
    private final String oldname = "oldname";
    private final String newname = "newname";
    private final String desc = "description";

    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("configuration.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            return DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.username"),
                    config.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenCreateItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item(oldname, desc));
            assertThat(tracker.findByName(oldname).size(), is(1));
        }
    }

    @Test
    public void whenReplaceItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            int id = tracker.add(new Item(oldname, desc)).getId();
            assertThat(tracker.findByName(oldname).size(), is(1));
            tracker.replace(id, new Item(newname, desc));
            assertThat(tracker.findByName(oldname).size(), is(0));
            assertThat(tracker.findByName(newname).size(), is(1));
        }
    }

    @Test
    public void whenDeleteItem() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            int id = tracker.add(new Item(oldname, desc)).getId();
            assertThat(tracker.findByName(oldname).size(), is(1));
            tracker.delete(id);
            assertThat(tracker.findByName(oldname).size(), is(0));
        }
    }

    @Test
    public void whenfindAll() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item(oldname, desc));
            List<Item> all = tracker.findAll();
            assertThat(all.size(), is(1));
            assertThat(all.get(0).getName(), is(oldname));
        }
    }

    @Test
    public void whenfindByName() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item(oldname, desc));
            assertThat(tracker.findByName(oldname).get(0).getName(), is(oldname));
        }
    }

    @Test
    public void whenfindById() throws Exception {
        try (TrackerSQL tracker = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            int id = tracker.add(new Item(oldname, desc)).getId();
            assertThat(tracker.findById(id).getName(), is(oldname));
        }
    }
}