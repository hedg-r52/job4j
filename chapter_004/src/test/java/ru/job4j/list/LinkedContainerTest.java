package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса LinkedContainer
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LinkedContainerTest {
    LinkedContainer<String> lc;

    @Before
    public void setUp() {
        lc = new LinkedContainer<>();
    }

    @Test
    public void whenAddItemThenGetFirstItem() {
        lc.add("1");
        assertThat(lc.get(0), is("1"));
    }

    @Test
    public void whenIterateThenInputValuesEqualsNextValues() {
        lc.add("2");
        lc.add("3");
        Iterator<String> it = lc.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("2"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("3"));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenIterateAndAddNewItemThenGetException() {
        lc.add("1");
        lc.add("2");
        Iterator<String> it = lc.iterator();
        assertThat(it.next(), is("1"));
        lc.add("3");
        it.next();
    }

    @Test
    public void whenDeleteByIndexFirstElement() {
        lc.add("1");
        lc.add("2");
        lc.add("3");
        lc.delete(0);
        assertThat(lc.get(0), is("2"));
        assertThat(lc.get(1), is("3"));
    }

    @Test
    public void whenDeleteByIndexAtTheMiddleOfList() {
        lc.add("1");
        lc.add("2");
        lc.add("3");
        lc.delete(1);
        assertThat(lc.get(0), is("1"));
        assertThat(lc.get(1), is("3"));
    }

    @Test
    public void whenAddFirstItemThenFirstItemNew() {
        lc.add("1");
        lc.add("2");
        lc.addFirst("3");
        assertThat(lc.get(0), is("3"));
        assertThat(lc.get(1), is("1"));
        assertThat(lc.get(2), is("2"));
    }
}
