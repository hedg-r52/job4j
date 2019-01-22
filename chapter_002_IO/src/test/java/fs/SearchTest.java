package fs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SearchTest {
    private static final String FS = System.getProperty("file.separator");
    private final String path = System.getProperty("java.io.tmpdir") + FS + "search";
    private final int count = 5;
    private final Search search = new Search();

    @Before
    public void setUp() throws Exception {
        Files.createDirectory(Paths.get(path));
        for (int i = 0; i < count; i++) {
            Files.createFile(Paths.get(path + FS + i + ".txt"));
            Files.createFile(Paths.get(path + FS + i + ".tmp"));
            Files.createDirectory(Paths.get(path + FS + i));
            for (int j = 0; j < count; j++) {
                Files.createFile(Paths.get(path + FS + i + FS + j + ".tmp"));
                Files.createFile(Paths.get(path + FS + i + FS + j + ".txt"));
            }
        }

    }

    @Test
    public void whenFilesTxtThenGet30Values() {
        List result = search.files(path, Arrays.asList("txt"));
        assertThat(result.size(), is(30));
    }

    @Test
    public void whenFilesTmpThenGet30Values() {
        List result = search.files(path, Arrays.asList("tmp"));
        assertThat(result.size(), is(30));
    }

    @Test
    public void whenFilesTxtAndTmpThenGet60Values() {
        List result = search.files(path, Arrays.asList("txt", "tmp"));
        assertThat(result.size(), is(60));
    }

    @Test
    public void whenFilesIniThenGetNoValues() {
        List result = search.files(path, Arrays.asList("ini"));
        assertThat(result.size(), is(0));
    }

    @After
    public void tearDown() throws Exception {
        for (int i = 0; i < count; i++) {
            Files.deleteIfExists(Paths.get(path + FS + i + ".txt"));
            Files.deleteIfExists(Paths.get(path + FS + i + ".tmp"));
            for (int j = 0; j < count; j++) {
                Files.deleteIfExists(Paths.get(path + FS + i + FS + j + ".txt"));
                Files.deleteIfExists(Paths.get(path + FS + i + FS + j + ".tmp"));
            }
            Files.deleteIfExists(Paths.get(path + FS + i));
        }
        Files.deleteIfExists(Paths.get(path));
    }
}