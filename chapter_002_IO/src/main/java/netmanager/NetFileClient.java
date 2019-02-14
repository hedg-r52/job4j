package netmanager;

import ru.job4j.utils.config.Config;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NetFileClient {
    private final static String CONFIG_FILE = "app.properties";
    private final static String LN = System.getProperty("line.separator");
    private final Socket socket;
    private final String root;
    private final PrintWriter out;
    private final BufferedReader in;

    public NetFileClient(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String request;
        String response;
        do {
            System.out.printf("$>");
            request = scanner.nextLine();
            out.println(request);
            if (!Command.QUIT.getName().equals(request)) {
                response = in.readLine();
                String[] partsResponse = response.split(" ");
                if ("TEXT".equals(partsResponse[0])) {
                    response = in.readLine();
                    while (response != null && !response.isEmpty()) {
                        System.out.println(response);
                        response = in.readLine();
                    }
                } else {
                    if ("DATADOWNLOAD".equals(partsResponse[0])) {
                        String filename = partsResponse[1];
                        saveFileFromSocket(filename);
                    } else if ("DATAUPLOAD".equals(partsResponse[0])) {
                        String filename = partsResponse[1];
                        sendFileToSocket(filename);
                    }
                }
            }
        } while (!Command.QUIT.getName().equals(request.toUpperCase()));
    }

    private void sendFileToSocket(String filename) throws IOException {
        out.println(Files.size(Paths.get(filename)));
        if ("OK".equals(in.readLine())) {
            byte[] buffer = new byte[4096];
            try (DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
                 FileInputStream fis = new FileInputStream(filename)) {
                while (fis.read(buffer) > 0) {
                    dos.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFileFromSocket(String filename) throws IOException {
        //
    }

    public static void main(String[] args) throws IOException {
        Config config = new Config(CONFIG_FILE);
        int port = Integer.valueOf(config.get("server.port"));
        try (Socket socket = new Socket(InetAddress.getByName(config.get("server.addr")), port)) {
            new NetFileClient(socket, "E:\\Temp\\client").start();
        }
    }
}

