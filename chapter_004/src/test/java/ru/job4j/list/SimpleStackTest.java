package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса SimpleStack
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStackTest {

    private SimpleStack<String> ss;
    @Before
    public void setUp() {
        ss = new SimpleStack<>();
    }

    @Test
    public void whenPushOneItemThenGetThisOne() {
        ss.push("1");
        assertThat(ss.poll(), is("1"));
    }

    @Test
    public void checkLIFO() {
        ss.push("1");
        ss.push("2");
        ss.push("3");
        assertThat(ss.poll(), is("3"));
        assertThat(ss.poll(), is("2"));
        assertThat(ss.poll(), is("1"));
    }
}