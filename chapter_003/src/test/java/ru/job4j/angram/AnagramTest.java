package ru.job4j.angram;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тесты класса Anagram
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class AnagramTest {

    @Test
    public void whenLetterSetMatchInitSetThenTrue() {
        Anagram anagram = new Anagram("мама");
        boolean result = anagram.check("амма");
        assertThat(result, is(true));
    }

    @Test
    public void whenLetterSetNotMatchInitSetThenFalse() {
        Anagram anagram = new Anagram("мама");
        boolean result = anagram.check("мааа");
        assertThat(result, is(false));
    }

    @Test
    public void whenLetterSetMoreThanInitSetThenFalse() {
        Anagram anagram = new Anagram("мама");
        boolean result = anagram.check("мааам");
        assertThat(result, is(false));
    }

    @Test
    public void whenLetterSetLessThanInitSetThenFalse() {
        Anagram anagram = new Anagram("мама");
        boolean result = anagram.check("ма");
        assertThat(result, is(false));
    }
}