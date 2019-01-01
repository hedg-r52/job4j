package checker;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CheckerTest {

    @Test
    public void whenInEvenThenResultTrue() {
        this.testIsEvenNumber("12345678", true);
    }

    @Test
    public void whenInOddThenResultFalse() {
        this.testIsEvenNumber("123456789", false);
    }

    @Test(expected = NumberFormatException.class)
    public void whenInNotNumberThenGetNumberFormatException() {
        this.testIsEvenNumber("not number", false);
    }

    private void testIsEvenNumber(String input, boolean expected) {
        Checker checker = new Checker();
        try (InputStream result = new ByteArrayInputStream(input.getBytes())) {
            assertThat(checker.isEvenNumber(result), is(expected));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}