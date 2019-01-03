package checker;

import org.junit.Test;
import java.io.*;
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

    @Test
    public void whenInStreamEnglishThenGetOutStreamWithoutAbuseWords() {
        String[] abuse = {"second", "fourth"};
        testDropAbuse(
                "first second third fourth fifth", "first third fifth", abuse
        );
    }

    @Test
    public void whenInStreamRussianThenGetOutStreamWithoutAbuseWords() {
        String[] abuse = {"второй", "четвертый"};
        testDropAbuse(
                "первый второй третий четвертый пятый", "первый третий пятый", abuse
        );
    }

    private void testDropAbuse(String input, String expected, String[] abuse) {
        Checker checker = new Checker();
        try (
                InputStream in = new ByteArrayInputStream(input.getBytes());
                OutputStream out = new ByteArrayOutputStream()) {
            checker.dropAbuses(in, out, abuse);
            assertThat(out.toString().trim(), is(expected));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}