package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FactorialTest {

    @Test
    public void factorialFiveThen120() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(5);
        assertThat(result, is(120));
    }

    @Test
    public void factorialZeroThen1() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(1);
        assertThat(result, is(1));
    }
}
