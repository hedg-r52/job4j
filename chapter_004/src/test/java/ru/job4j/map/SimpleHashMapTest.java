package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса SimpleHashMap
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMapTest {
    SimpleHashMap<Integer, String> shm;

    @Before
    public void setUp() {
        shm = new SimpleHashMap<>();
    }

    @Test
    public void whenAddPairThenContainsThatPairReturnTrue() {
        shm.insert(1, "one");
        assertThat(shm.get(1), is("one"));
    }

    @Test
    public void whenRemovePairThenGetReturnNull() {
        shm.insert(1, "one");
        shm.delete(1);
        assertNull(shm.get(1));
    }

    @Test
    public void whenIterate() {
        shm.insert(1, "one");
        shm.insert(2, "two");
        Iterator<Integer> it = shm.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("one"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("two"));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void whenLogFactorOutBoundThenGrow() {
        shm.insert(1, "one");
        shm.insert(12, "twelve");
        shm.insert(23, "twenty three");
        shm.insert(34, "thirty four");
        shm.insert(45, "forty five");
        shm.insert(56, "fifty six");
        shm.insert(67, "sixty seven");
        Iterator<Integer> it = shm.iterator();
        assertThat(it.next(), is("one"));
        assertThat(it.next(), is("twenty three"));
        assertThat(it.next(), is("forty five"));
        assertThat(it.next(), is("sixty seven"));
        assertThat(it.next(), is("twelve"));
        assertThat(it.next(), is("thirty four"));
        assertThat(it.next(), is("fifty six"));
    }
}