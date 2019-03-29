package ru.job4j.utils.generators;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StringGenerator {
    private final Random random = new Random();

    public String generate(int length) {
        return random.ints(48, 123)
                .filter(i -> (i < 58 || i > 64) && (i < 91 || i > 96))
                .mapToObj(i -> (char) i)
                .limit(random.nextInt(length))
                .collect(StringBuilder::new, (sb, i) -> sb.append((char) i), StringBuilder::append)
                .toString();
    }

    public String getCharSequence(char symbol, int length) {
        char[] array = new char[length];
        Arrays.fill(array, symbol);
        return new String(array);
    }
}
