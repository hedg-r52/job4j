package chat;

import com.google.common.base.Joiner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConsoleChatTest {
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private final PrintStream ps = new PrintStream(out);
    private final String filename = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "phrases.txt";
    private final List<String> phrases = Arrays.asList(
            "Hello!",
            "How are you?",
            "I'm clever",
            "O'k",
            "Not at all",
            "You're kidding"
    );

    @Before
    public void setUp() throws Exception {
        String line = Joiner.on(System.lineSeparator()).join(phrases);
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(line);
        }
        System.setOut(ps);
    }

    @Test
    public void whenCheckPhrasesContainsReceivedStringsAndCountEqualsTwo() throws Exception {
        ConsoleChat chat = new ConsoleChat(
                filename,
                new StubInput(new String[] {"привет", "стоп", "тест", "продолжить", "тест", "закончить"})
        );
        chat.start();
        int count = 0;
        int expected = 2;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
             InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream);
             BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line = "";
            while ((line = br.readLine()) != null) {
                assertTrue(phrases.contains(line));
                count++;
            }
        }
        assertThat(expected, is(count));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(this.stdout);
        Files.deleteIfExists(Paths.get(filename));
    }
}
