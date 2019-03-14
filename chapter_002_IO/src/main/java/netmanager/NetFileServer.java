package netmanager;

import ru.job4j.utils.config.Config;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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
    private final Map<String, Function<String, Command>> commands = new HashMap<>();
    private final DataOutputStream out;
    private final DataInputStream in;
    private final String root;
    private final RequestParser requestParser = RequestParser.getRequestParser();
    private final SocketHelper socketHelper;
    private String currentDir;

    /**
     * Constructor
     *
     * @param socket
     * @param root   root directory
     * @throws IOException
     */
    public NetFileServer(Socket socket, String root) throws IOException {
        this.socket = socket;
        this.root = root;
        this.currentDir = root;
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
    public NetFileServer(Socket socket) throws IOException {
        this(socket, com.google.common.io.Files.createTempDir().getAbsolutePath());
    }

    /**
     * Method to start entry of NetFileServer
     *
     * @throws IOException
     */
    public void start() throws IOException {
        byte[] buffer;
        Command status = Command.IDLE;
        do {
            buffer = new byte[4096];
            in.read(buffer);
            String[] requests = new String(buffer).trim().split(LN);
            for (String request : requests) {
                requestParser.parse(request);
                status = this.action(requestParser.getCommand(), requestParser.getParam());
                if (status == Command.QUIT) {
                    break;
                }
            }
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
        this.load(Command.LIST.getName(), this::getList);
        this.load(Command.CD.getName(), this::changeDir);
        this.load(Command.UPLOAD.getName(), this::upload);
        this.load(Command.DOWNLOAD.getName(), this::download);
        this.load(Command.WHERE.getName(), this::where);
        this.load(Command.QUIT.getName(), this::quit);
        this.load(Command.IDLE.getName(), this::idle);
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
     * Exec command WHERE and get current directory
     *
     * @param param
     * @return
     */
    private Command where(String param) {
        try {
            out.write(currentDir.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Command.WHERE;
    }

    /**
     * Exec command LIST for get list of files/dirs current directory
     *
     * @param param
     * @return
     */
    private Command getList(String param) {
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
    }

    /**
     * Exec command CD for change directory
     *
     * @param param
     * @return
     */
    private Command changeDir(String param) {
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

    }

    /**
     * Exec command DOWNLOAD to download file
     *
     * @param param
     * @return
     */
    private Command download(String param) {
        try {
            String path = currentDir + FS + param;
            long size = Files.size(Paths.get(path));
            out.write(String.format("OK %s", size).getBytes());
            out.flush();
            socketHelper.sendFileToSocket(path, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Command.DOWNLOAD;
    }

    /**
     * Exec command UPLOAD to upload file
     *
     * @param param
     * @return
     */
    private Command upload(String param) {
        int lastSpace = param.lastIndexOf(" ");
        String path = param.substring(0, lastSpace);
        String filename = path.substring(path.lastIndexOf(FS) + 1);
        long size = Long.parseLong(param.substring(lastSpace + 1));
        socketHelper.saveFileFromSocket(currentDir + FS + filename, size);
        return Command.UPLOAD;
    }

    /**
     * Exec command QUIT for exit from main loop
     *
     * @param param
     * @return
     */
    private Command quit(String param) {
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

    public static void main(String[] args) throws IOException {
        Config config = new Config(CONFIG_FILE);
        try (Socket socket = new ServerSocket(Integer.valueOf(config.get("server.port"))).accept()) {
            new NetFileServer(socket).start();
        }
    }
}
