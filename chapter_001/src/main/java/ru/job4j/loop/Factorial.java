package ru.job4j.loop;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class Factorial {

    public int calc(int n) {
        int result = 1;
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                result *= i;
            }
        }
        return result;
    }

}
