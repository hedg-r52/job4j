package ru.job4j.jdbc.tracker;

import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerTest {

    @Ignore
    @Test
    public void whenCheckAllMethodsOfTracker() {
        try (Tracker tracker = new Tracker("configuration.properties")) {
            int recordCount = tracker.findAll().size();
            Item item = new Item("test name", "test description", 123L);

            tracker.add(item);
            assertThat(tracker.findAll().size(), is(recordCount + 1));

            assertThat(tracker.findByName("test name").contains(item), is(true));

            Item newItem = new Item("new replaced name", "new replaced description", 234L);
            newItem.setId(item.getId());
            tracker.replace(item.getId(), newItem);
            assertThat(tracker.findById(item.getId()), is(newItem));

            tracker.delete(item.getId());
            assertThat(tracker.findAll().size(), is(recordCount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}