package ru.job4j.loop;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class Counter {

    /**
     * Вычисление суммы четных чисел
     * @param start Начальное значение диапазона
     * @param finish Конечное значение диапазона
     * @return Сумма
     */
    public int add(int start, int finish) {
        int sum = 0;

        for (int i = start; i <= finish; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }

        return sum;
    }

}
