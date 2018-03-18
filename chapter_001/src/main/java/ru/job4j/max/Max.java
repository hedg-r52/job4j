package ru.job4j.max;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class Max {

    /**
     * Определение максимального из двух чисел
     * @param first первое число
     * @param second второе число
     * @return максимальное число
     */
    public int max(int first, int second) {
        return (first > second ? first : second);
    }
}
