package ru.job4j.utils.generators;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringGeneratorTest {

    @Test
    public void whenGenerateNotMoreValueThenStringLengthNotMoreValue() {
        StringGenerator sg = new StringGenerator(100);
        assertTrue(sg.generate().length() <= 100);
    }
}