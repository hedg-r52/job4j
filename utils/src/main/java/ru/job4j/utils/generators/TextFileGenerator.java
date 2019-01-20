package ru.job4j.utils.generators;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TextFileGenerator {
    private final String path;
    private final int lines;

    private final StringGenerator stringGenerator;

    public TextFileGenerator(String path, int lineLength, int countLines) {
        this.path = path;
        this.lines = countLines;
        this.stringGenerator = new StringGenerator(lineLength);
    }

    public void generate() throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(stringGenerator.generate());
            for (int i = 1; i < lines; i++) {
                fw.write(System.getProperty("line.separator"));
                fw.write(stringGenerator.generate());
            }
        }
    }
}
