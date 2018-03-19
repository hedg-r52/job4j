package ru.job4j.array;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {

    @Test
    public void whenBound4ThenMaxPow16() {
        Square square = new Square();
        int[] result = square.calculate(4);
        int[] expected = new int[] {1, 4, 9, 16};
        assertArrayEquals(expected, result);
    }
}