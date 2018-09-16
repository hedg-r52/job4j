package func;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ComputeTest {
    Compute c;
    final int start = 1;
    final int end = 5;

    @Before
    public void setUp() {
        c = new Compute();
    }

    @Test
    public void whenTestLinearFunction() {
        List<Double> result = c.diapason(start, end, (x) -> (2 * x + 5));
        List<Double> expected = Arrays.asList(7D, 9D, 11D, 13D);
        assertThat(expected, is(result));
    }

    @Test
    public void whenTestQuadraticFunction() {
        List<Double> result = c.diapason(start, end, (x) -> (Math.pow(x, 2) + 5));
        List<Double> expected = Arrays.asList(6D, 9D, 14D, 21D);
        assertThat(expected, is(result));
    }

    @Test
    public void whenTestLogarifmicFunction() {
        List<Double> result = c.diapason(start, end, (x) -> (Math.log(x)));
        List<Double> expected = Arrays.asList(0D, 0.6931471805599453D, 1.0986122886681098D, 1.3862943611198906D);
        assertThat(expected, is(result));
    }
}