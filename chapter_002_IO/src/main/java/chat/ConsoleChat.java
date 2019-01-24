package chat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConsoleChat {
    private boolean quit = false;
    private boolean silent = false;
    private final Input input;
    private String file;
    String line;
    Random random;

    public ConsoleChat(String file, Input input) {
        this.file = file;
        this.input = input;
        random = new Random();
    }

    public ConsoleChat(String file) {
        this(file, new ConsoleInput());
    }

    public void start() {
        while (!quit) {
            line = input.ask(">");
            if ("закончить".equals(line)) {
                quit = true;
            } else if ("стоп".equals(line)) {
                silent = true;
            } else if ("продолжить".equals(line)) {
                silent = false;
            } else {
                if (!silent) {
                    System.out.println(getText());
                }
            }
        }
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
