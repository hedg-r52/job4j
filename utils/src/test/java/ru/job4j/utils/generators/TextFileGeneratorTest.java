package ru.job4j.utils.generators;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TextFileGeneratorTest {
    private final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    private final static String FILE = TMP_DIR + System.getProperty("file.separator") + "file.txt";


    @Test
    public void whenGenerateThenGeneratedLinesEqualsLinesAtFile() throws IOException {
        int lineLength = 40;
        int countLines = 10;
        TextFileGenerator tfg = new TextFileGenerator(FILE, lineLength, countLines);
        tfg.generate();
        assertThat((int) Files.lines(Paths.get(FILE)).count(), is(countLines));
    }

    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(FILE));
    }
}