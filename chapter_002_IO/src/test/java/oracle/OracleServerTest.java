package oracle;

import com.google.common.base.Joiner;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
public class OracleServerTest {
    private final static String LN = System.getProperty("line.separator");
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;

    @Test
    public void whenAskAnswerThenChooseRandom() throws IOException {
        OracleServer server = getOracleServer("exit");
        server.start();
        assertThat(this.out.toString(), is(""));
    }

    @Test
    public void whenAskHelloThenBackGreatings() throws IOException {

        OracleServer server = getOracleServer(
                Joiner.on(LN).join(
                        "hello",
                        "exit"
                )
        );
        server.start();
        assertThat(this.out.toString(),
                is(
                        String.format("Hello, dear friend, I'm a oracle.%s%s", LN, LN)
                )
        );
    }

    private OracleServer getOracleServer(String data) throws IOException {
        Socket socket = mock(Socket.class);
        this.out = new ByteArrayOutputStream();
        this.in = new ByteArrayInputStream(data.getBytes());
        when(socket.getInputStream()).thenReturn(this.in);
        when(socket.getOutputStream()).thenReturn(this.out);
        return new OracleServer(socket);
    }
}
