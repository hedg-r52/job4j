package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса SimpleSet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleSetTest {

    @Test
    public void testAdd() {
        SimpleSet<String> sa = new SimpleSet<>();
        sa.add("first");
        sa.add("second");
        Iterator<String> it = sa.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("second"));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testAddSomeSameValues() {
        SimpleSet<String> sa = new SimpleSet<>();
        sa.add("first");
        sa.add("first");
        sa.add("second");
        Iterator<String> it = sa.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("first"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("second"));
        assertThat(it.hasNext(), is(false));
    }
}