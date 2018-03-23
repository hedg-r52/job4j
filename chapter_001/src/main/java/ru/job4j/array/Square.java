package ru.job4j.array;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class Square {

    /**
     * Вывод чисел возведенными в квадрат
     * @param bound граница
     * @return строку с квадратами чисел от 1 до bound
     */
    public int[] calculate(int bound) {
        int[] rst = new int[bound];

        for (int i = 1; i <= bound; i++) {
            rst[i - 1] = (int) Math.pow(i, 2);
        }

        return rst;
    }

}
