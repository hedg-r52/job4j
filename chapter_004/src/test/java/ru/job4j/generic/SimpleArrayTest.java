package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест класса SimpleArray
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayTest {

    @Test
    public void whenAddItemThenGetSameItem() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.add(1);
        int result = sa.get(0);
        assertThat(result, is(1));
    }

    @Test
    public void whenChangeItemThenGetNewItem() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.add(1);
        sa.set(0, 5);
        int result = sa.get(0);
        assertThat(result, is(5));
    }

    @Test
    public void whenAddThreeItemsAndDeleteSecondItemThenGetSecondEqualsThird() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.add(1);
        sa.add(2);
        sa.add(3);
        sa.delete(1);
        int result = sa.get(1);
        assertThat(result, is(3));
    }

    @Test
    public void testNextMethodOfIterator() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.add(1);
        sa.add(2);
        Iterator<Integer> iter = sa.iterator();
        assertThat(iter.next(), is(1));
        assertThat(iter.next(), is(2));
    }

    @Test
    public void testHasNextMethodOfIterator() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.add(1);
        sa.add(2);
        Iterator<Integer> iter = sa.iterator();
        assertThat(iter.hasNext(), is(true));
        iter.next();
        assertThat(iter.hasNext(), is(true));
        iter.next();
        assertThat(iter.hasNext(), is(false));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenEmptyArrayThenGetException() {
        SimpleArray<Integer> sa = new SimpleArray<Integer>(10);
        sa.get(0);
    }
}
