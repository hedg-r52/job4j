package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TurnTest {

    @Test
    public void whenEvenNumElementsBack() {
        Turn turn = new Turn();
        int[] result = turn.back(new int[] {4, 1, 6, 2});
        int[] expected = new int[] {2, 6, 1, 4};
        assertThat(result, is(expected));
    }

    @Test
    public void whenOddNumElementsBack() {
        Turn turn = new Turn();
        int[] result = turn.back(new int[] {1, 2, 3, 4, 5});
        int[] expected = new int[] {5, 4, 3, 2, 1};
        assertThat(result, is(expected));
    }

}