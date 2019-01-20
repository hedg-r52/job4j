package ru.job4j.utils.generators;

import java.util.Random;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StringGenerator {
    private final int length;
    private final Random random;

    public StringGenerator(int length) {
        this.length = length;
        random = new Random();
    }

    public String generate() {
        return random.ints(48, 123)
                .filter(i -> (i < 58 || i > 64) && (i < 91 || i > 96))
                .mapToObj(i -> (char) i)
                .limit(random.nextInt(length))
                .collect(StringBuilder::new, (sb, i) -> sb.append((char) i), StringBuilder::append)
                .toString();
    }
}
