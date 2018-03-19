package ru.job4j.array;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class FindLoopTest {

    @Test
    public void whenFoundElement3AtArrayWithIndex2() {
        FindLoop fl = new FindLoop();
        int result = fl.indexOf(new int[] {1, 2, 3, 4, 5}, 3);
        int expected = 2;
        assertThat(result, is(expected));
    }

    @Test
    public void whenNotFoundElement6AtArray() {
        FindLoop fl = new FindLoop();
        int result = fl.indexOf(new int[] {1, 2, 3, 4, 5}, 6);
        int expected = -1;
        assertThat(result, is(expected));
    }
}