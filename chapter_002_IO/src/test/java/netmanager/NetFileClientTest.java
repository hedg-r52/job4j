package netmanager;

import java.io.*;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NetFileClientTest {
    private final static String LN = System.getProperty("line.separator");
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;
    private final InputStream systemIn = System.in;
    private File root;

    @Before
    public void setUp() {
        root = Files.createTempDir();
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(root);
    }

    @Test
    public void whenWhereThenOutContainsWhereAndQuitCommands() throws IOException {
           String data = String.format("where%squit%s", LN, LN);
            ByteArrayInputStream pseudoIn = new ByteArrayInputStream(data.getBytes());
            System.setIn(pseudoIn);
            NetFileClient client = getNetFileClient(data);
            client.start();
            assertThat(this.out.toString(), is("WHEREQUIT"));
            System.setIn(systemIn);
    }

    private NetFileClient getNetFileClient(String data) throws IOException {
        Socket socket = mock(Socket.class);
        this.out = new ByteArrayOutputStream();
        this.in = new ByteArrayInputStream(data.getBytes());
        when(socket.getInputStream()).thenReturn(this.in);
        when(socket.getOutputStream()).thenReturn(this.out);
        return new NetFileClient(socket, root.getAbsolutePath());
    }
}
