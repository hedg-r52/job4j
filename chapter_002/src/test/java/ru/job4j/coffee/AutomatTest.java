package ru.job4j.coffee;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AutomatTest {

    @Test
    public void whenMakeCoffeAndPutFiftyBanknoteThenAutomatGetTenAndFiveCoins() {
        Automat automat = new Automat(new int[] {1, 2, 5, 10}, 35);
        int[] result = automat.changes(50, 35);
        int[] expected = {10, 5};
        assertThat(result, is(expected));
    }
}