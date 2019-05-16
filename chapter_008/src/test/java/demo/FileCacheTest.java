package demo;

import cache.FileCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FileCacheTest {

    private List<String> filenames = Arrays.asList("Names.txt", "Address.txt");

    @Before
    public void setUp() throws Exception {
        for (String filename : filenames) {
            Path f = Paths.get(filename);
            Files.write(f, Arrays.asList(filename), Charset.defaultCharset());
        }
    }

    @After
    public void tearDown() throws Exception {
        for (String filename : filenames) {
            Files.deleteIfExists(Paths.get(filename));
        }
    }

    @Test
    public void whenGetTwoValuesFromCacheShouldSaveToCacheTwoValues() {
        FileCache cache = new FileCache();
        assertThat(cache.size(), is(0));
        String result0 = cache.get(filenames.get(0)).trim();
        String result1 = cache.get(filenames.get(1)).trim();
        assertThat(cache.size(), is(2));
        assertThat(result0, is(filenames.get(0)));
        assertThat(result1, is(filenames.get(1)));
        String result2 = cache.get(filenames.get(0)).trim();
        String result3 = cache.get(filenames.get(1)).trim();
        assertThat(cache.size(), is(2));
        assertThat(result2, is(filenames.get(0)));
        assertThat(result3, is(filenames.get(1)));

    }
}