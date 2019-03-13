package netmanager;

import ru.job4j.utils.config.Config;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NetFileClient {
    private final static String CONFIG_FILE = "app.properties";
    private final static String LN = System.getProperty("line.separator");
    private final static String FS = System.getProperty("file.separator");
    private final static String PROMPT = "$>";
    private final Socket socket;
    private final String root;
    private final DataOutputStream out;
    private final DataInputStream in;
    private Command status;
    private final Map<String, BiFunction<String, String, Command>> commands = new HashMap<>();

    /**
     * Constructor
     * @param socket
     * @param root root directory
     * @throws IOException
     */
    public NetFileClient(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        this.status = Command.IDLE;
        out = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        this.init();
    }

    /**
     * Constructor
     * @param socket
     * @throws IOException
     */
    public NetFileClient(Socket socket) throws IOException {
        this(socket,  com.google.common.io.Files.createTempDir().getAbsolutePath());
    }

    /**
     * Method to start entry of NetFileClient
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String request;
        do {
            System.out.printf(PROMPT);
            request = scanner.nextLine();
            String[] args = request.split(" ", 2);
            String param = (args.length > 1 ? args[1] : "");
            status = this.action(args[0].toUpperCase(), param);
        } while (status != Command.QUIT);
    }

    /**
     * Apply action and return command
     * @param cmd name of command
     * @param param param of command (may be empty
     * @return
     */
    private Command action(final String cmd, final String param) {
        Command result = Command.IDLE;
        if (this.commands.keySet().contains(cmd)) {
            result = this.commands.get(cmd).apply(cmd, param);
        } else {
            this.commands.get(Command.IDLE.getName()).apply(Command.IDLE.getName(), "");
        }
        return result;
    }

    /**
     * loading commands in hashmap
     */
    private void init() {
        this.load(Command.WHERE.getName(), handleWhere());
        this.load(Command.LIST.getName(), handleGetList());
        this.load(Command.CD.getName(), handleChangeDir());
        this.load(Command.DOWNLOAD.getName(), handleDownload());
        this.load(Command.UPLOAD.getName(), handleUpload());
        this.load(Command.IDLE.getName(), handleIdle());
        this.load(Command.QUIT.getName(), handleQuit());
    }

    /**
     * put handle in hashmap
     * @param cmd name of command
     * @param handle handle
     */
    private void load(String cmd, BiFunction<String, String, Command> handle) {
        this.commands.put(cmd, handle);
    }

    private BiFunction<String, String, Command> handleWhere() {
        return  (cmd, param) -> {
            try {
                byte[] buffer = new byte[4096];
                out.write(Command.WHERE.getName().getBytes());
                out.flush();
                in.read(buffer);
                String line = new String(buffer);
                System.out.printf("Current directory: %s%s", line, LN);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.WHERE;
        };
    }

    private BiFunction<String, String, Command> handleGetList() {
        return (cmd, param) -> {
            try {
                byte[] buffer = new byte[4096];
                out.write(Command.LIST.getName().getBytes());
                out.flush();
                in.read(buffer);
                String line = new String(buffer);
                System.out.print(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.LIST;
        };
    }

    private BiFunction<String, String, Command> handleChangeDir() {
        return (cmd, param) -> {
            try {
                byte[] buffer = new byte[4096];
                out.write(String.format("%s %s", Command.CD.getName(), param).getBytes());
                out.flush();
                in.read(buffer);
                String line = new String(buffer);
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.LIST;
        };
    }

    private BiFunction<String, String, Command> handleDownload() {
        return (cmd, param) -> {
            try {
                byte[] buffer = new byte[4096];
                out.write(String.format("%s %s", Command.DOWNLOAD.getName(), param).getBytes());
                out.flush();
                in.read(buffer);
                String line = new String(buffer);
                if ("OK".equals(line.split(" ", 2)[0])) {
                    String filename = param.substring(param.lastIndexOf(FS) + 1);
                    long size = Long.parseLong(line.split(" ")[1]);
                    saveFileFromSocket(filename, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.DOWNLOAD;
        };
    }

    private BiFunction<String, String, Command> handleUpload() {
        return (cmd, param) -> {
            try {
                byte[] buffer = new byte[4096];
                String filename = getFilename(param);
                long size = Files.size(Paths.get(filename));
                out.write(String.format("%s %s %s", Command.UPLOAD.getName(), param, size).getBytes());
                out.flush();
                in.read(buffer);
                String line = new String(buffer);
                if ("OK".equals(line.split(" ", 2)[0])) {
                    sendFileToSocket(filename, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.UPLOAD;
        };
    }

    private BiFunction<String, String, Command> handleQuit() {
        return (cmd, param) -> {
            try {
                out.write(Command.QUIT.getName().getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.QUIT;
        };
    }

    private BiFunction<String, String, Command> handleIdle() {
        return (cmd, param) -> Command.IDLE;
    }

    /**
     * Save file from socket
     * @param filename full path to file
     * @param size size of sending file
     * @throws IOException
     */
    private void saveFileFromSocket(String filename, long size) throws IOException {
        int count;
        int totalWrite = 0;
        long remaining = size;
        byte[] buffer = new byte[4096];
        try (FileOutputStream fos = new FileOutputStream(root + FS + filename)) {
            while ((count = in.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                totalWrite += count;
                remaining -= count;
                fos.write(buffer, 0, count);
            }
            System.out.println("write " + totalWrite + " bytes.");
        }
    }

    /**
     * Send file from disk to socket
     * @param filename full path to file
     * @param size size of sending fil
     * @throws IOException
     */
    private void sendFileToSocket(String filename, long size) throws IOException {
        try {
            int count;
            int totalRead = 0;
            long remaining = size;
            byte[] buffer = new byte[4096];
            try (FileInputStream fis = new FileInputStream(filename)) {
                while ((count = fis.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                    totalRead += count;
                    remaining -= count;
                    out.write(buffer, 0, count);
                    out.flush();
                }
                System.out.println("send " + totalRead + " bytes.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get filename
     *
     * If filename contains file separator, it's absolutely path,
     * if not - specified file's directory is root directory of client app
     *
     * @param path input file path
     * @return absolutely file path or filename in root directory of client app
     */
    private String getFilename(String path) {
        return (path.contains(FS) ? path : root + FS + path);
    }

    public static void main(String[] args) throws IOException {
        Config config = new Config(CONFIG_FILE);
        int port = Integer.valueOf(config.get("server.port"));
        try (Socket socket = new Socket(InetAddress.getByName(config.get("server.addr")), port)) {
            new NetFileClient(socket).start();
        }
    }
}
