package netmanager;

import ru.job4j.utils.config.Config;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NetFileServer {
    private final static String CONFIG_FILE = "app.properties";
    private final static String LN = System.getProperty("line.separator");
    private final static String FS = System.getProperty("file.separator");
    private final Socket socket;
    private final Map<String, BiFunction<String, String, Command>> commands = new HashMap<>();
    private final DataOutputStream out;
    private final DataInputStream in;
    private final String root;
    private Command status;
    private String currentDir;

    /**
     * Constructor
     * @param socket
     * @param root root directory
     * @throws IOException
     */
    public NetFileServer(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        this.currentDir = root;
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
    public NetFileServer(Socket socket) throws IOException {
        this(socket, com.google.common.io.Files.createTempDir().getAbsolutePath());
    }

    /**
     * Method to start entry of NetFileServer
     * @throws IOException
     */
    public void start() throws IOException {
        byte[] buffer;
        do {
            buffer = new byte[4096];
            in.read(buffer);
            String[] requests = new String(buffer).trim().split(LN);
            for (String request : requests) {
                String[] args = request.split(" ", 2);
                String param = (args.length > 1 ? args[1] : "");
                status = this.action(args[0].toUpperCase(), param);
                if (status == Command.QUIT) {
                    break;
                }
            }
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
        this.load(Command.LIST.getName(), handleGetList());
        this.load(Command.CD.getName(), handleChangeDir());
        this.load(Command.DOWNLOAD.getName(), handleDownload());
        this.load(Command.UPLOAD.getName(), handleUpload());
        this.load(Command.WHERE.getName(), handleWhere());
        this.load(Command.QUIT.getName(), handleQuit());
        this.load(Command.IDLE.getName(), handleIdle());
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
        return (cmd, param) -> {
            try {
                out.write(currentDir.getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.WHERE;
        };
    }

    private BiFunction<String, String, Command> handleGetList() {
        return (cmd, param) -> {
            StringBuilder sb = new StringBuilder();
            try (Stream<Path> paths = Files.walk(Paths.get(currentDir))) {
                sb.append(String.format("Current directory: %s%s", currentDir, LN));
                paths
                        .filter(p -> !Paths.get(currentDir).equals(p))
                        .forEach(p -> {
                    String type = Files.isDirectory(p) ? "[DIR]" : "[FILE]";
                    sb.append(String.format("%6s %s%s", type, p.toString(), LN));
                });
                out.write(sb.toString().getBytes());
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.LIST;
        };
    }

    private BiFunction<String, String, Command> handleChangeDir() {
        return (cmd, param) -> {
            if ("..".equals(param)) {
                if (!root.equals(currentDir)) {
                    int position = currentDir.lastIndexOf(FS);
                    currentDir = currentDir.substring(0, position);
                    try {
                        out.write(String.format("Directory changed to \"%s\"", currentDir).getBytes());
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    String newCurrentDir = String.format("%s%s%s", currentDir, FS, param);
                    if (Files.exists(Paths.get(newCurrentDir)) && Files.isDirectory(Paths.get(newCurrentDir))) {
                        currentDir = String.format("%s%s%s", currentDir, FS, param);
                        out.write(String.format("Directory changed to \"%s\"", currentDir).getBytes());
                    } else {
                        out.write("Directory does not exist".getBytes());
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Command.CD;
        };
    }

    private BiFunction<String, String, Command> handleDownload() {
        return (cmd, param) -> {
            // check if file exist
            try {
                String path = currentDir + FS + param;
                long size = Files.size(Paths.get(path));
                out.write(String.format("OK %s", size).getBytes());
                out.flush();
                sendFileToSocket(path, size);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.DOWNLOAD;
        };
    }

    private BiFunction<String, String, Command> handleUpload() {
        return (cmd, param) -> {
            int lastSpace = param.lastIndexOf(" ");
            String path = param.substring(0, lastSpace);
            String filename = path.substring(path.lastIndexOf(FS) + 1);
            long size = Long.parseLong(param.substring(lastSpace + 1));
            saveFileFromSocket(filename, size);
            return Command.UPLOAD;
        };
    }

    private BiFunction<String, String, Command> handleQuit() {
        return (cmd, param) -> Command.QUIT;
    }

    private BiFunction<String, String, Command> handleIdle() {
        return (cmd, param) -> Command.IDLE;
    }

    /**
     * Send file from disk to socket
     * @param filename full path to file
     * @param size size of sending file
     * @throws IOException
     */
    private void sendFileToSocket(String filename, long size) throws IOException {
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
    }

    /**
     * Save file from socket
     * @param filename full path to file
     * @param size size of sending file
     */
    private void saveFileFromSocket(String filename, long size) {
        int count;
        int totalWrite = 0;
        long remaining = size;
        byte[] buffer = new byte[4096];
        try (FileOutputStream fos = new FileOutputStream(currentDir + FS + filename)) {
            out.write("OK".getBytes());
            out.flush();
            while ((count = in.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                totalWrite += count;
                remaining -= count;
                fos.write(buffer, 0, count);
            }
            System.out.println("write " + totalWrite + " bytes.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exec(String dir) {

    }

    public static void main(String[] args) throws IOException {
        Config config = new Config(CONFIG_FILE);
        try (Socket socket = new ServerSocket(Integer.valueOf(config.get("server.port"))).accept()) {
            new NetFileServer(socket).start();
        }
    }
}
