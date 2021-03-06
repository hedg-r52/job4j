package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayCharTest {

    @Test
    public void whenStartWithPrefixThenTrue() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("He");
        assertThat(result, is(true));
    }

    @Test
    public void whenNotStartWithPrefixThenFalse() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("Hi");
        assertThat(result, is(false));
    }

    @Test
    public void whenContainsSub() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.contains("ll");
        assertThat(result, is(true));
    }

    @Test
    public void whenNotContainsSub() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.contains("low");
        assertThat(result, is(false));
    }

}