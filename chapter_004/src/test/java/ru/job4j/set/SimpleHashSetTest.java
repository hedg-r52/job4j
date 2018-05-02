package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса SimpleHashSet
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSetTest {
    private SimpleHashSet<Integer> hs;

    @Before
    public void setUp() {
        hs = new SimpleHashSet<>();
    }

    @Test
    public void whenAddNumberThenContainsThatNumberReturnTrue() {
        assertThat(hs.contains(1), is(false));
        hs.add(1);
        assertThat(hs.contains(1), is(true));
    }

    @Test
    public void whenRemoveNumberThenContainsReturnFalse() {
        hs.add(1);
        assertThat(hs.contains(1), is(true));
        hs.remove(1);
        assertThat(hs.contains(1), is(false));
    }

    @Test
    public void whenIterate() {
        hs.add(1);
        hs.add(2);
        Iterator<Integer> it = hs.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void whenLogFactorOutBoundThenGrow() {
        hs.add(1);
        hs.add(12);
        hs.add(23);
        hs.add(34);
        hs.add(45);
        hs.add(56);
        hs.add(67);
        Iterator<Integer> it = hs.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.next(), is(23));
        assertThat(it.next(), is(45));
        assertThat(it.next(), is(67));
        assertThat(it.next(), is(12));
        assertThat(it.next(), is(34));
        assertThat(it.next(), is(56));
    }
}