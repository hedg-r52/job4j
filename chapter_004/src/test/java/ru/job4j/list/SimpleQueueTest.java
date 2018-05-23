package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса SimpleQueue
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueueTest {
    private SimpleQueue<String> sq;

    @Before
    public void setUp() {
        sq = new SimpleQueue<>();
    }

    @Test
    public void whenPushOneItemThenGetThisOne() {
        sq.push("1");
        assertThat(sq.poll(), is("1"));
    }

    @Test
    public void checkFIFO() {
        sq.push("1");
        sq.push("2");
        sq.push("3");
        assertThat(sq.poll(), is("1"));
        assertThat(sq.poll(), is("2"));
        assertThat(sq.poll(), is("3"));
    }
}