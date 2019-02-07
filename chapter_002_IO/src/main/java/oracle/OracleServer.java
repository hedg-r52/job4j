package oracle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class OracleServer {
    private Socket socket;
    private static final int PORT = 1111;


    public OracleServer(Socket socket) {
        this.socket = socket;
    }

    public void start() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        String ask;
        do {
            System.out.println("wait command...");
            ask = in.readLine();
            System.out.println(ask);
            if ("hello".equals(ask)) {
                out.println("Hello, dear friend, I'm a oracle.");
                out.println();
            } else {
                out.println("I don't understand");
                out.println("!!!");
                out.println();
            }
        } while (!"exit".equals(ask));
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new ServerSocket(PORT).accept()) {
            new OracleServer(socket).start();
        }
    }
}
