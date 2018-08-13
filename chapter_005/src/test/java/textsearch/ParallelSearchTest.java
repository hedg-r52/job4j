package textsearch;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    private String root = System.getProperty("java.io.tmpdir");
    private final List<String> dirs = new ArrayList<>(
            Arrays.asList("d\\d1\\d1_1", "d\\d1\\d1_2", "d\\d2\\d2_1", "d\\d2\\d2_2")
            );
    private final Set<String> allValues = new LinkedHashSet<>(
            Arrays.asList(
                    String.format("%s%s", root, "d\\d1\\d1_1\\file.lst"),
                    String.format("%s%s", root, "d\\d1\\d1_1\\file.txt"),
                    String.format("%s%s", root, "d\\d1\\d1_2\\file.lst"),
                    String.format("%s%s", root, "d\\d1\\d1_2\\file.txt"),
                    String.format("%s%s", root, "d\\d2\\d2_1\\file.lst"),
                    String.format("%s%s", root, "d\\d2\\d2_1\\file.txt"),
                    String.format("%s%s", root, "d\\d2\\d2_2\\file.lst"),
                    String.format("%s%s", root, "d\\d2\\d2_2\\file.txt")
            )
    );
    private final Set<String> pieValues = new LinkedHashSet<>(
            Arrays.asList(
                    String.format("%s%s", root, "d\\d1\\d1_1\\file.lst"),
                    String.format("%s%s", root, "d\\d1\\d1_2\\file.lst"),
                    String.format("%s%s", root, "d\\d2\\d2_1\\file.lst"),
                    String.format("%s%s", root, "d\\d2\\d2_2\\file.lst")
            )
    );

    @Before
    public void before() {
        int counter = 1;
        for (String dir : dirs) {
            File file = new File(String.format("%s\\%s", root, dir));
            if (!file.exists()) {
                file.mkdirs();
            }
            if (file.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s\\%s\\file.txt", root, dir)))) {
                    writer.write(String.format("%s%d", "frog", counter++));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s\\%s\\file.lst", root, dir)))) {
                    writer.write("pie frog");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @After
    public void after() {
        try {
            FileUtils.forceDelete(new File(String.format("%s\\%s", root, "d")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void whenTextFrogSearchingThenGetAllValues() {
       ParallelSearch ps = new ParallelSearch(root, "frog", Arrays.asList("txt", "lst"));
       ps.init();
       assertThat(ps.result().equals(allValues), is(true));
    }

    @Test
    public void whenTextPieSearchingThenGetOnlyLstFiles() {
        ParallelSearch ps = new ParallelSearch(root, "pie", Arrays.asList("txt", "lst"));
        ps.init();
        assertThat(ps.result().equals(pieValues), is(true));
    }
}
