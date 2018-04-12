package ru.job4j.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Конвертер из листа в двумерный массив
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ConvertList2Array {
    public int[][] toArray(List<Integer> list, int rows) {
        int cells = list.size() / rows + (list.size() % rows != 0 ? 1 : 0);
        int[][] array = new int[rows][cells];
        int row = 0;
        int cell = 0;
        for (int i: list) {
            array[row][cell++] = i;
            if (cell == cells) {
                row++;
                cell = 0;
            }
        }
        return array;
    }

    public List<Integer> convert(List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] arr : list) {
            for (int elem : arr) {
                result.add(elem);
            }
        }
        return result;
    }
}
