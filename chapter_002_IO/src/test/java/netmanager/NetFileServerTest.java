package netmanager;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NetFileServerTest {
    private final static String LN = System.getProperty("line.separator");
    private final static String FS = System.getProperty("file.separator");
    private final static String DIR = "Dir1";
    private final static String FILE = "test.txt";
    private final static String TEXT = "testing";
    private String dirPath;
    private String filePath;
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;
    File tempDir;

    @Before
    public void setUp() throws Exception {
        tempDir = Files.createTempDir();
        dirPath = tempDir.getAbsolutePath() + FS + DIR;
        new File(dirPath).mkdir();
        filePath = tempDir.getAbsolutePath() + FS + FILE;
        createTempTextFile(filePath);
    }

    private void createTempTextFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            writer.append(TEXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(tempDir);
    }

    @Test
    public void whenQuit() throws IOException {
        NetFileServer server = getNetFileServer("QUIT");
        server.start();
        assertThat(this.out.toString(),
                is(
                        String.format("")
                )
        );
    }

    @Test
    public void whenListThenGetListing() throws IOException {
        NetFileServer server = getNetFileServer(
                Joiner.on(LN).join(
                        "LIST",
                        "QUIT"
                )
        );
        server.start();
        String file = String.format("[FILE] %s", filePath);
        String dir = String.format("[DIR] %s", dirPath);
        String current = String.format("Current directory: %s", tempDir.getAbsolutePath());
        assertTrue(this.out.toString().contains(file));
        assertTrue(this.out.toString().contains(dir));
        assertTrue(this.out.toString().contains(current));
    }

    @Test
    public void whenChangeDirThenGetNewDirectory() throws IOException {
        NetFileServer server = getNetFileServer(
                Joiner.on(LN).join(
                        String.format("CD %s", DIR),
                        "QUIT"
                )
        );
        server.start();
        assertThat(this.out.toString(),
                is(
                        String.format("Directory changed to \"%s\"", dirPath)
                )
        );
    }

    @Test
    public void whenDownload() throws IOException {
        NetFileServer server = getNetFileServer(
                Joiner.on(LN).join(
                        String.format("DOWNLOAD %s", FILE),
                        "QUIT"
                )
        );
        server.start();
        assertThat(this.out.toString(),
                is(
                        String.format("OK %s%s", TEXT.length(), TEXT)
                )
        );
    }

    @Test
    public void whenUpload() throws IOException {
        long size = java.nio.file.Files.size(Paths.get(filePath));
        NetFileServer server = getNetFileServer(
                Joiner.on(LN).join(
                        String.format("CD %s", DIR),
                        String.format("UPLOAD %s %s", filePath, size),
                        "QUIT"
                )
        );
        server.start();
        assertThat(this.out.toString(),
                is(
                        String.format("Directory changed to \"%s\"%s", dirPath, "OK")
                )
        );
    }

    @Test
    public void whenWhere() throws IOException {
        NetFileServer server = getNetFileServer(
                Joiner.on(LN).join(
                        "WHERE",
                        "QUIT"
                )
        );
        server.start();
        assertThat(this.out.toString(),
                is(
                        tempDir.getAbsolutePath()
                )
        );
    }

    private NetFileServer getNetFileServer(String data) throws IOException {
        Socket socket = mock(Socket.class);
        this.out = new ByteArrayOutputStream();
        this.in = new ByteArrayInputStream(data.getBytes());
        when(socket.getInputStream()).thenReturn(this.in);
        when(socket.getOutputStream()).thenReturn(this.out);
        return new NetFileServer(socket, tempDir.getAbsolutePath());
    }
}
