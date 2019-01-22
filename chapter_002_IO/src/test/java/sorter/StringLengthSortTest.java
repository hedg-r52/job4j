package sorter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.utils.generators.TextFileGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringLengthSortTest {
    private final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    private final static String IN = TMP_DIR + System.getProperty("file.separator") + "in.txt";
    private final static String OUT = TMP_DIR + System.getProperty("file.separator") + "out.txt";
    private final int lineLength = 40;
    private final int countLines = 10;

    @Before
    public void setUp() throws IOException {
        TextFileGenerator tfg = new TextFileGenerator(IN, lineLength, countLines);
        tfg.generate();
    }

    @Test
    public void whenSortThenNextStringLengthMoreOrEquals() throws Exception {
        StringLengthSort sort = new StringLengthSort(3);
        File in = new File(IN);
        File out = new File(OUT);
        sort.sort(in, out);
        try (FileReader fr = new FileReader(out);
             BufferedReader br = new BufferedReader(fr)) {
            int currentLength = 0;
            String s = br.readLine();
            while (s != null) {
                assertTrue(currentLength <= s.length());
                currentLength = s.length();
                s = br.readLine();
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(IN));
        Files.deleteIfExists(Paths.get(OUT));
    }
}