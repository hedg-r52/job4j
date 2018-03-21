package ru.job4j.array;

import org.hamcrest.core.Is;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SortedArrayTest {

    @Test
    public void whenJoinFirstArraysLessSecondArray() {
        SortedArray sa = new SortedArray();
        int[] result = sa.join(new int[] {2, 4}, new int[] {1, 3, 5, 7});
        int[] expected = new int[] {1, 2, 3, 4, 5, 7};
        assertThat(result, Is.is(expected));

    }

    @Test
    public void whenJoinFirstArraysMoreSecondArray() {
        SortedArray sa = new SortedArray();
        int[] result = sa.join(new int[] {1, 3, 5, 7}, new int[] {2, 4});
        int[] expected = new int[] {1, 2, 3, 4, 5, 7};
        assertThat(result, Is.is(expected));

    }
}
