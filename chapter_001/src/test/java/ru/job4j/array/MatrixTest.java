package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MatrixTest {

    @Test
    public void multiple2x2() {
        Matrix matrix = new Matrix();
        int[][] result = matrix.multiple(2);
        int[][] expected = new int[][] {{1, 2}, {2, 4}};

        assertThat(result, is(expected));
    }

    @Test
    public void multiple3x3() {
        Matrix matrix = new Matrix();
        int[][] result = matrix.multiple(3);
        int[][] expected = new int[][] {
                {1, 2, 3},
                {2, 4, 6},
                {3, 6, 9}
        };

        assertThat(result, is(expected));
    }
}