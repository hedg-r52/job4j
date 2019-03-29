package ru.job4j.utils.generators;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringGeneratorTest {

    @Test
    public void whenGenerateNotMoreValueThenStringLengthNotMoreValue() {
        StringGenerator sg = new StringGenerator();
        assertTrue(sg.generate(100).length() <= 100);
    }
}