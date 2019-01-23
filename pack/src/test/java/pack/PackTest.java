package pack;

import com.google.common.base.Joiner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.*;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PackTest {
    private static final String FS = System.getProperty("file.separator");
    private final String path = System.getProperty("java.io.tmpdir") + FS + "archive";
    private final String output =  System.getProperty("java.io.tmpdir") + FS + "archive.zip";
    private final int count = 5;
    private final List<String> archiveExts = Arrays.asList("txt", "tmp");
    private final List<String> allExts = Arrays.asList("txt", "tmp", "ini");

    @Before
    public void setUp() throws Exception {
        Files.createDirectory(Paths.get(path));
        for (int i = 0; i < count; i++) {
            for (String ext : allExts) {
                Files.createFile(Paths.get(String.format("%s%s%s.%s", path, FS, i, ext)));
            }
        }
    }

    @Test
    public void whenExecThenArchiveContainsFilesOnlyArchiveExtensions() throws Exception {
        Pack pack = new Pack(path, archiveExts, output);
        pack.exec();
        String matchString = ".*\\.[" + Joiner.on("|").join(archiveExts) + "]+";
        try (FileInputStream fis = new FileInputStream(output);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                assertTrue(entry.getName().matches(matchString));
            }
        }
    }

    @Test
    public void whenExecThenArchiveNotContainsFilesOtherThanArchiveExtensions() throws Exception {
        Pack pack = new Pack(path, archiveExts, output);
        pack.exec();
        List<String> otherExts = new ArrayList<>(allExts);
        otherExts.removeAll(archiveExts);
        String matchString = ".*\\.[" + Joiner.on("|").join(otherExts) + "]+";
        try (FileInputStream fis = new FileInputStream(output);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                assertFalse(entry.getName().matches(matchString));
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        for (int i = 0; i < count; i++) {
            for (String ext : allExts) {
                Files.deleteIfExists(Paths.get(String.format("%s%s%s.%s", path, FS, i, ext)));
            }
        }
        Files.deleteIfExists(Paths.get(path));
        Files.deleteIfExists(Paths.get(output));
    }
}
