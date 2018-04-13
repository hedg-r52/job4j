package ru.job4j.tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StubInputTest {
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void loadMem() {
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenAskOneFromMenuThenResultEqualsOne() {
        StubInput input = new StubInput(new String[] {"1"});
        Tracker tracker = new Tracker();
        MenuTracker menu = new MenuTracker(input, tracker);
        menu.fillActions();
        List<Integer> ranges = menu.getRange();
        int result = input.ask("Enter", ranges);
        assertThat(result, is(1));
    }

    @Test(expected = MenuOutException.class)
    public void whenAskEightFromMenuThenGetMenuOutException() {
        StubInput input = new StubInput(new String[] {"8"});
        Tracker tracker = new Tracker();
        MenuTracker menu = new MenuTracker(input, tracker);
        menu.fillActions();
        List<Integer> ranges = menu.getRange();
        int result = input.ask("Enter", ranges);
    }
}