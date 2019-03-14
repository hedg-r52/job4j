package netmanager;

import ru.job4j.utils.config.Config;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

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
    private final RequestParser requestParser = RequestParser.getRequestParser();
    private final SocketHelper socketHelper;
    private final Map<String, Function<String, Command>> commands = new HashMap<>();

    /**
     * Constructor
     *
     * @param socket
     * @param root   root directory
     * @throws IOException
     */
    public NetFileClient(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        out = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
        in = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));
        this.socketHelper = new SocketHelper(this.in, this.out);
        this.init();
    }

    /**
     * Constructor
     *
     * @param socket
     * @throws IOException
     */
    public NetFileClient(Socket socket) throws IOException {
        this(socket, com.google.common.io.Files.createTempDir().getAbsolutePath());
    }

    /**
     * Method to start entry of NetFileClient
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Command status = Command.IDLE;
        String request;
        do {
            System.out.printf(PROMPT);
            request = scanner.nextLine();
            requestParser.parse(request);
            status = this.action(requestParser.getCommand(), requestParser.getParam());
        } while (status != Command.QUIT);
    }

    /**
     * Apply action and return command
     *
     * @param cmd   name of command
     * @param param param of command (may be empty
     * @return
     */
    private Command action(final String cmd, final String param) {
        return (this.commands.keySet().contains(cmd)
                ? this.commands.get(cmd).apply(param)
                : this.commands.get(Command.IDLE.getName()).apply("")
        );
    }

    /**
     * loading commands in hashmap
     */
    private void init() {
        this.load(Command.WHERE.getName(), this::where);
        this.load(Command.LIST.getName(), this::getList);
        this.load(Command.CD.getName(), this::changeDir);
        this.load(Command.DOWNLOAD.getName(), this::download);
        this.load(Command.UPLOAD.getName(), this::upload);
        this.load(Command.IDLE.getName(), this::idle);
        this.load(Command.QUIT.getName(), this::quit);
    }

    /**
     * put handle in hashmap
     *
     * @param cmd    name of command
     * @param handle handle
     */
    private void load(String cmd, Function<String, Command> handle) {
        this.commands.put(cmd, handle);
    }


    /**
     * Send command WHERE and get current directory
     *
     * @param param
     * @return
     */
    private Command where(String param) {
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
    }

    /**
     * Send command LIST for get list of files/dirs current directory
     *
     * @param param
     * @return
     */
    private Command getList(String param) {
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
    }

    /**
     * Send command CD for change directory
     *
     * @param param
     * @return
     */
    private Command changeDir(String param) {
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
    }

    /**
     * Send command DOWNLOAD to download file
     *
     * @param param
     * @return
     */
    private Command download(String param) {
        try {
            byte[] buffer = new byte[4096];
            out.write(String.format("%s %s", Command.DOWNLOAD.getName(), param).getBytes());
            out.flush();
            in.read(buffer);
            String line = new String(buffer);
            if ("OK".equals(line.split(" ", 2)[0])) {
                String filename = param.substring(param.lastIndexOf(FS) + 1);
                long size = Long.parseLong(line.split(" ")[1]);
                socketHelper.saveFileFromSocket(filename, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Command.DOWNLOAD;
    }

    /**
     * Send command UPLOAD to upload file
     *
     * @param param
     * @return
     */
    private Command upload(String param) {
        try {
            byte[] buffer = new byte[4096];
            String filename = getFilename(param);
            long size = Files.size(Paths.get(filename));
            out.write(String.format("%s %s %s", Command.UPLOAD.getName(), param, size).getBytes());
            out.flush();
            in.read(buffer);
            String line = new String(buffer);
            if ("OK".equals(line.split(" ", 2)[0])) {
                socketHelper.sendFileToSocket(filename, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Command.UPLOAD;
    }

    /**
     * Send command QUIT for exit from main loop
     *
     * @param param
     * @return
     */
    private Command quit(String param) {
        try {
            out.write(Command.QUIT.getName().getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Command.QUIT;
    }

    /**
     * Idle operation
     *
     * @param param
     * @return
     */
    private Command idle(String param) {
        return Command.IDLE;
    }

    /**
     * Get filename
     * <p>
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
