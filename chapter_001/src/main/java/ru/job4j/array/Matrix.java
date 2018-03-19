package ru.job4j.array;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Matrix {

    /**
     * Возвращает таблицу размером size на size с таблицей умножения
     * @param size размер таблицы умножения
     * @return массив с таблицей умножения
     */
    public int[][] multiple(int size) {
        int[][] array = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = (i+1) * (j+1);
            }
        }

        return array;
    }
}
