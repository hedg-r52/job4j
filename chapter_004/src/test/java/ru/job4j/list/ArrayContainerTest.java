package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса ArrayContainer
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayContainerTest {
    ArrayContainer<String> ac;

    @Before
    public void setUp() {
        ac = new ArrayContainer<>(2);
    }

    @Test
    public void whenAddItemThenGetItemZero() {
        ac.add("1");
        assertThat(ac.get(0), is("1"));
    }

    @Test
    public void whenAddThreeItemThenArrayContainerGrow() {
        ac.add("1");
        ac.add("2");
        ac.add("3");
        assertThat(ac.get(2), is("3"));
    }

    @Test
    public void whenIterateThenInputValuesEqualsNextValues() {
        ac.add("1");
        ac.add("2");
        Iterator<String> it = ac.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("1"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("2"));
        assertThat(it.hasNext(), is(false));
    }
}
