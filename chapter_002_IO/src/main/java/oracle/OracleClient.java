package oracle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class OracleClient {
    private Socket socket;
    private static final int PORT = 1111;

    public OracleClient(Socket socket) {
        this.socket = socket;
    }

    public void start() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        Scanner console = new Scanner(System.in);
        String request;
        String response;
        do {
            request = console.nextLine();
            out.println(request);
            if (!"exit".equals(request)) {
                response = in.readLine();
                while (!response.isEmpty()) {
                    System.out.println(response);
                }
            }
        } while (!"exit".equals(request));
    }


    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(InetAddress.getByName("localhost"), PORT)) {
            new OracleClient(socket).start();
        }
    }
}
