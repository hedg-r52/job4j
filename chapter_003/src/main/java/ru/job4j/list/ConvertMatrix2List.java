package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер из двумерного массива в лист
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConvertMatrix2List {
    public List<Integer> toList(int[][] array) {
        List<Integer> list = new ArrayList<>();
        for (int[] cells : array) {
            for (int cell : cells) {
                list.add(cell);
            }
        }
        return list;
    }
}
