package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.list.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrackerTest {

    @Test
    public void whenAddItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        assertThat(tracker.findAll().get(0), is(item));
    }

    @Test
    public void whenReplaceItemThenTrackerHasSameItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        Item replaceItem = new Item("replace", "replaceDescription", 1234L);
        tracker.replace(tracker.findAll().get(0).getId(), replaceItem);
        assertThat(tracker.findAll().get(0), is(replaceItem));
    }

    @Test
    public void whenAddThreeItemsDeleteFirstItemThenTrackerFindByIdNull() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription", 123L);
        tracker.add(item1);
        Item item2 = new Item("test2", "testDescription", 123L);
        tracker.add(item2);
        Item item3 = new Item("test3", "testDescription", 123L);
        tracker.add(item3);
        String id = tracker.findAll().get(0).getId();
        tracker.delete(id);
        assertNull(tracker.findById(id));
    }


    @Test
    public void whenFindAllLengthEqualsTwo() {
        Tracker tracker = new Tracker();
        Item item1 = new Item("test1", "testDescription1", 123L);
        Item item2 = new Item("test2", "testDescription2", 123L);
        tracker.add(item1);
        tracker.add(item2);
        assertThat(tracker.findAll().size(), is(2));
    }

    @Test
    public void whenFindByNameArrayEqualsAnonArrayWithItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        List<Item> list = new ArrayList<>();
        assertThat(tracker.findByName("test2"), is(list));
        list.add(item);
        assertThat(tracker.findByName("test"), is(list));

    }



    @Test
    public void whenFindByIdItemEqualsItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("test", "testDescription", 123L);
        tracker.add(item);
        String id = tracker.findAll().get(0).getId();
        assertThat(tracker.findById(id), is(item));

    }
}