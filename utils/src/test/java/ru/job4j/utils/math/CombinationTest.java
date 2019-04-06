package ru.job4j.utils.math;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class CombinationTest {
    @Test
    public void whenListOfOneTwoShouldListOfListOneTwoAndTwoOne() {
        List<List<Integer>> result = Combination.generate(Arrays.asList(1, 2));
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(2, 1)
        );
        assertThat(result, is(expected));
    }

    @Test
    public void whenListOfMulAddShoulListOfListMulMulAndMulPlusAndPlusMulAndPlusPlus() {
        List<List<String>> result = Combination.generateEveryWithEvery(Arrays.asList("*", "+"), 2);
        List<List<String>> expected = Arrays.asList(
                Arrays.asList("*", "*"),
                Arrays.asList("*", "+"),
                Arrays.asList("+", "*"),
                Arrays.asList("+", "+")
        );
        assertThat(result, is(expected));
    }
}