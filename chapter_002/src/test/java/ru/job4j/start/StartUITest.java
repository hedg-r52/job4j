package ru.job4j.start;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.tracker.Input;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.StubInput;
import ru.job4j.tracker.Tracker;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        assertThat(tracker.findAll()[0].getName(), is("test name")); // проверяем, что нулевой элемент массива в трекере содержит имя, введённое при эмуляции.
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
        assertThat(tracker.findAll().length, is(0));
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
        assertThat(tracker.findByName("test name"), is(new Item[] {item}));
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
                                .append("------------ Список заявок --------------\r\n")
                                .append("ID : ")
                                .append(item.getId())
                                .append("  Заявка: test\r\n")
                                .append("Описание : \r\n")
                                .append("desc\r\n")
                                .append("------------\r\n")
                                .append(System.lineSeparator())
                                .append("------------ Конец списка заявок --------------\r\n")
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
                .append("Меню\r\n")
                .append("0. Добавить новую заявку\r\n")
                .append("1. Показать все заявки\r\n")
                .append("2. Редактировать заявку\r\n")
                .append("3. Удалить заявку\r\n")
                .append("4. Найти заявку по ID\r\n")
                .append("5. Найти заявки по имени\r\n")
                .append("6. Выход из программы\r\n")
                .toString();
    }
}
