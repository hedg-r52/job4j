package barbell;

import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BarbellTest {
    private final static int LIMIT = 10_000;

    @Test
    public void whenInWeightsOneTwoThreeSixShouldReturnTwelve() {
        Barbell barbell = new Barbell(LIMIT);
        int expected = 12;
        int result = barbell.maxWeight(Arrays.asList(1, 2, 3, 6));
        assertThat(result, is(expected));
    }

    @Test
    public void whenInWeightsOneTwoThreeFourFiveSixShouldReturnTwenty() {
        Barbell barbell = new Barbell(LIMIT);
        int expected = 20;
        int result = barbell.maxWeight(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(result, is(expected));
    }

    @Test
    public void whenInWeightsOneTwoShouldReturnZero() {
        Barbell barbell = new Barbell(LIMIT);
        int expected = 0;
        int result = barbell.maxWeight(Arrays.asList(1, 2));
        assertThat(result, is(expected));
    }

    @Test
    public void whenInWeightsThreeFourThreeThreeTwoShouldReturnTwelve() {
        Barbell barbell = new Barbell(LIMIT);
        int expected = 12;
        int result = barbell.maxWeight(Arrays.asList(3, 4, 3, 3, 2));
        assertThat(result, is(expected));
    }
}