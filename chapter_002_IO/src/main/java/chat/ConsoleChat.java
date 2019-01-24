package chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConsoleChat {
    private final Input input;
    private final String file;
    private final Random random;
    private Status status;
    private final Map<String, Function<String, Status>> dispatch = new HashMap<>();

    public ConsoleChat(String file, Input input) {
        this.file = file;
        this.input = input;
        random = new Random();
        status = Status.VERBOSE;
        this.init();
    }

    public ConsoleChat(String file) {
        this(file, new ConsoleInput());
    }

    public void start() {
        while (status != Status.QUIT) {
            status = action(input.ask(">"));
            if (status == Status.VERBOSE) {
                System.out.println(getText());
            }
        }
    }

    private Function<String, Status> handleQuit() {
        return msg -> {
            return Status.QUIT;
        };
    }

    private Function<String, Status> handleSilent() {
        return msg -> {
            return Status.SILENT;
        };
    }

    private Function<String, Status> handleVerbose() {
        return msg -> {
            return Status.VERBOSE;
        };
    }

    private void load(String str, Function<String, Status> handle) {
        this.dispatch.put(str, handle);
    }

    private void init() {
        this.load("закончить", this.handleQuit());
        this.load("стоп", this.handleSilent());
        this.load("продолжить", this.handleVerbose());
    }

    public Status action(final String line) {
        Status result = status;
        if (this.dispatch.keySet().contains(line)) {
            result = this.dispatch.get(line).apply(line);
        }
        return result;
    }

    private String getText() {
        String result = "";
        try (Stream<String> lines = Files.lines(Paths.get(this.file))) {
            int count = (int) Files.lines(Paths.get(this.file)).count();
            result = lines.skip(random.nextInt(count - 1)).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
