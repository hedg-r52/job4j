package ru.job4j.max;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class MaxTest {

    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        int result = maxim.max(1, 2);
        assertThat(result, is(2));
    }

    @Test
    public void whenFirstMoreSecond() {
        Max maxim = new Max();
        int result = maxim.max(2, 1);
        assertThat(result, is(2));
    }

    @Test
    public void whenFirstMoreThanSecondAndThird() {
        Max maxim = new Max();
        int result = maxim.max(3, 2, 1);
        assertThat(result, is(3));
    }

    @Test
    public void whenSecondMoreThanFirstAndThird() {
        Max maxim = new Max();
        int result = maxim.max(2, 3, 1);
        assertThat(result, is(3));
    }

    @Test
    public void whenThirdMoreThanFirstAndSecond() {
        Max maxim = new Max();
        int result = maxim.max(2, 1, 3);
        assertThat(result, is(3));
    }
}
