package ru.job4j.utils.generators;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TextFileGenerator {
    private final StringGenerator stringGenerator = new StringGenerator();

    public void generate(String path, int countLines, int length) throws IOException {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(stringGenerator.generate(length));
            for (int i = 1; i < countLines; i++) {
                fw.write(System.getProperty("line.separator"));
                fw.write(stringGenerator.generate(length));
            }
        }
    }
}
