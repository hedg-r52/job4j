package netmanager;

import ru.job4j.utils.config.Config;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final static String FS = System.getProperty("file.separator");
    private final Socket socket;
    private final Map<String, BiFunction<String, String, Command>> commands = new HashMap<>();
    private final PrintWriter out;
    private final BufferedReader in;
    private final DataInputStream dis;
    private final String root;
    private Command status;
    private String currentDir;

    public NetFileServer(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        this.currentDir = root;
        this.status = Command.IDLE;
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        dis = new DataInputStream(this.socket.getInputStream());
        this.init();
    }

    public void start() throws IOException {
        String line;
        do {
            line = in.readLine();
            String[] args = line.split(" ", 2);
            String param = (args.length > 1 ? args[1] : "");
            status = this.action(args[0].toUpperCase(), param);
        } while (status != Command.QUIT);
    }

    private Command action(final String cmd, final String param) {
        Command result = Command.IDLE;
        if (this.commands.keySet().contains(cmd)) {
            result = this.commands.get(cmd).apply(cmd, param);
        } else {
            this.commands.get(Command.IDLE.getName()).apply(Command.IDLE.getName(), "");
        }
        return result;
    }

    private void init() {
        this.load(Command.LIST.getName(), handleGetList());
        this.load(Command.CD.getName(), handleChangeDir());
        this.load(Command.DOWNLOAD.getName(), handleDownload());
        this.load(Command.UPLOAD.getName(), handleUpload());
        this.load(Command.WHERE.getName(), handleWhere());
        this.load(Command.QUIT.getName(), handleQuit());
        this.load(Command.IDLE.getName(), handleIdle());
    }

    private void load(String cmd, BiFunction<String, String, Command> handle) {
        this.commands.put(cmd, handle);
    }

    private BiFunction<String, String, Command> handleGetList() {
        return (cmd, param) -> {
            out.println("TEXT");
            out.println(String.format("Current directory: %s", currentDir));
            try (Stream<Path> paths = Files.walk(Paths.get(currentDir))) {
                paths.forEach(p -> {
                    String type = Files.isDirectory(p) ? "[DIR]" : "[FILE]";
                    out.println(String.format("%6s %s", type, p.toString()));
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.println();
            return Command.LIST;
        };
    }

    private BiFunction<String, String, Command> handleChangeDir() {
        return (cmd, param) -> {
            if ("..".equals(param)) {
                if (!root.equals(currentDir)) {
                    int position = currentDir.lastIndexOf(FS);
                    currentDir = currentDir.substring(0, position);
                }
            } else {
                String newCurrentDir = String.format("%s%s%s", currentDir, FS, param);
                if (Files.exists(Paths.get(newCurrentDir)) && Files.isDirectory(Paths.get(newCurrentDir))) {
                    currentDir = String.format("%s%s%s", currentDir, FS, param);
                } else {
                    out.println("Directory does not exist");
                }
            }
            out.println("");
            return Command.CD;
        };
    }

    private BiFunction<String, String, Command> handleDownload() {
        // TODO download
        return (cmd, param) -> {
            out.println("");
            return Command.DOWNLOAD;
        };
    }

    private BiFunction<String, String, Command> handleUpload() {
        return (cmd, param) -> {
            out.println(String.format("DATAUPLOAD %s", param));
            String filename = param.substring(param.lastIndexOf(FS) + 1);
            try {
                saveFileFromSocket(currentDir + FS + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Command.UPLOAD;
        };
    }

    /**
     * Save file from socket
     *   first, read size of bytes
     *   then read data
     * @param filename name of new file
     * @throws IOException
     */
    private void saveFileFromSocket(String filename) throws IOException {
        int size = Integer.valueOf(in.readLine());
        out.println("OK");
        int count;
        int totalRead = 0;
        int remaining = size;
        byte[] buffer = new byte[4096];
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            while ((count = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += count;
                remaining -= count;
                System.out.println("read " + totalRead + " bytes.");
                fos.write(buffer, 0, count);
            }
        }
    }

    private BiFunction<String, String, Command> handleQuit() {
        return (cmd, param) -> {
            out.println();
            return Command.QUIT;
        };
    }

    private BiFunction<String, String, Command> handleIdle() {
        return  (cmd, param) -> {
            out.println("");
            return Command.IDLE;
        };
    }

    private BiFunction<String, String, Command> handleWhere() {
        return  (cmd, param) -> {
            out.println(currentDir);
            out.println();
            return Command.WHERE;
        };
    }

    public static void main(String[] args) throws IOException {
        Config config = new Config(CONFIG_FILE);
        int port = Integer.valueOf(config.get("server.port"));
        try (Socket socket = new ServerSocket(port).accept()) {
            new NetFileServer(socket, "E:\\Temp\\server").start();
        }
    }
}
