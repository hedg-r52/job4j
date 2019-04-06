package mathparser;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MathParserTest {
    @Test
    public void whenFourOneSevenEightAndCanBeEqual24ShouldTrue() {
        MathParser parser = new MathParser();
        assertTrue(parser.canBeEqualTo24(Arrays.asList(4, 1, 7, 8)));
    }

    @Test
    public void whenTwoOneTwoOneAndCanBeEqual24ShouldFalse() {
        MathParser parser = new MathParser();
        assertFalse(parser.canBeEqualTo24(Arrays.asList(2, 1, 2, 1)));
    }
}
