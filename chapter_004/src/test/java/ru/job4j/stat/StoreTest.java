package ru.job4j.stat;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StoreTest {

    @Test
    public void whenOneAddedZeroChangedTwoDeletedThenGetResultOneZeroTwo() {
        List<Store.User> previous = new ArrayList<>();
        previous.add(new Store.User(1, "first"));
        previous.add(new Store.User(2, "second"));
        previous.add(new Store.User(3, "third"));
        List<Store.User> current = new ArrayList<>();
        current.add(new Store.User(3, "third"));
        current.add(new Store.User(4, "fourth"));
        Store store = new Store();
        assertThat(store.diff(previous, current), is(new Info(1, 0, 2)));
    }

    @Test
    public void whenOneAddedTwoChangedOneDeletedThenGetResultOneTwoOne() {
        List<Store.User> previous = new ArrayList<>();
        previous.add(new Store.User(1, "first"));
        previous.add(new Store.User(2, "second"));
        previous.add(new Store.User(3, "third"));
        List<Store.User> current = new ArrayList<>();
        current.add(new Store.User(2, "два"));
        current.add(new Store.User(3, "три"));
        current.add(new Store.User(4, "fourth"));
        Store store = new Store();
        assertThat(store.diff(previous, current), is(new Info(1, 2, 1)));
    }

    @Test
    public void whenListEqualsThenAllResultsZero() {
        List<Store.User> previous = new ArrayList<>();
        previous.add(new Store.User(1, "first"));
        List<Store.User> current = new ArrayList<>();
        current.add(new Store.User(1, "first"));
        Store store = new Store();
        assertThat(store.diff(previous, current), is(new Info(0, 0, 0)));
    }

}