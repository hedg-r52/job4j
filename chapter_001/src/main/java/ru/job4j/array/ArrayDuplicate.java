package ru.job4j.array;

import java.util.Arrays;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ArrayDuplicate {

    /**
     * Удаляет дубликаты и возвращает в новый массив
     * @param array входной массив
     * @return выходной массив
     */
    public String[] remove(String[] array) {
        int numDupl = 0;
        String word;

        for (int i = 0; i < array.length - numDupl - 1; i++) {
            word = array[i];
            for (int j = i + 1; j < array.length - numDupl; ) {
                if ( word.equals(array[j]) ) {
                    numDupl++;
                    array[j] = array[array.length - numDupl];
                } else {
                    j++;
                }
            }
        }

        return Arrays.copyOf( array, array.length - numDupl);

    }
}
