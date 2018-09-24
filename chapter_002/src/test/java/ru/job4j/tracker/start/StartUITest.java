package ru.job4j.tracker.start;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StartUITest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void whenAddItemThenTrackerHasNewItemWithSameName() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"0", "test name", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().get(0).getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
    }

    @Test
    public void whenUpdateThenTrackerHasUpdatedValue() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        Input input = new StubInput(new String[]{"2", item.getId(), "test name updated", "desc", "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is("test name updated"));
    }

    @Test
    public void whenDeleteThenTrackerHasOneItemLess() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        Input input = new StubInput(new String[]{"3", item.getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findAll().size(), is(0));
    }

    @Test
    public void whenFindByIdNewItemThenTrackerHasItemWithSameName() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        Input input = new StubInput(new String[]{"4", item.getId(), "6"});
        new StartUI(input, tracker).init();
        assertThat(tracker.findById(item.getId()).getName(), is(item.getName()));
    }

    @Test
    public void whenFindByNameNewItemThenTrackerHasSameArray() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test name", "desc"));
        Input input = new StubInput(new String[]{"5", "test", "6"});
        new StartUI(input, tracker).init();
        List<Item> result = new ArrayList<>();
        result.add(item);
        assertThat(tracker.findByName("test name"), is(result));
    }

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.setOut(stdout);
    }

    @Test
    public void whenUserSelectFindAll() {
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("test", "desc"));
        Input input = new StubInput(new String[]{"1", "6"});
        new StartUI(input, tracker).init();
        assertThat(
                this.out.toString(),
                is(
                        new StringBuilder()
                                .append(this.getMenu())
                                .append("------------ Список заявок --------------")
                                .append(System.lineSeparator())
                                .append("ID : ")
                                .append(item.getId())
                                .append("  Заявка: test")
                                .append(System.lineSeparator())
                                .append("Описание : desc")
                                .append(System.lineSeparator())
                                .append("------------")
                                .append(System.lineSeparator())
                                .append(System.lineSeparator())
                                .append("------------ Конец списка заявок --------------")
                                .append(System.lineSeparator())
                                .append(this.getMenu())
                                .toString()
                ));
    }

    @Test
    public void whenUserEnterAndQuit() {
        Tracker tracker = new Tracker();
        Input input = new StubInput(new String[]{"6"});
        new StartUI(input, tracker).init();
        assertThat(this.out.toString(), is(this.getMenu()));
    }

    private String getMenu() {
        return new StringBuilder()
                .append("0. Add the new item.")
                .append(System.lineSeparator())
                .append("1. Show all items.")
                .append(System.lineSeparator())
                .append("2. Edit item.")
                .append(System.lineSeparator())
                .append("3. Delete item.")
                .append(System.lineSeparator())
                .append("4. Find item by id.")
                .append(System.lineSeparator())
                .append("5. Find items by name.")
                .append(System.lineSeparator())
                .append("6. Exit Program.")
                .append(System.lineSeparator())
                .toString();
    }
}
