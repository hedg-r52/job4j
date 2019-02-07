package oracle;

import org.junit.Test;
import java.io.*;
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
public class OracleClientTest {
    private final static String LN = System.getProperty("line.separator");
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;
    private final InputStream systemIn = System.in;

    @Test
    public void whenAskAnswerThenChooseRandom() throws IOException {
        String data = String.format("hello%sexit%s", LN, LN);
        ByteArrayInputStream pseudoIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(pseudoIn);
        OracleClient client = getOracleClient();
        client.start();
        assertThat(this.out.toString(), is(data));
        System.setIn(systemIn);
    }

    private OracleClient getOracleClient() throws IOException {
        Socket socket = mock(Socket.class);
        this.out = new ByteArrayOutputStream();
        this.in = new ByteArrayInputStream(
                String.format("Hello, dear friend, I'm a oracle.%s%s", LN, LN).getBytes()
        );
        when(socket.getInputStream()).thenReturn(this.in);
        when(socket.getOutputStream()).thenReturn(this.out);
        return new OracleClient(socket);
    }
}
