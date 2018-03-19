package ru.job4j.array;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class Turn {

    /**
     * Переворачивает массив
     * @param array массив целых чисел
     * @return перевернутый массив
     */
    public int[] back(int[] array) {
        int mirror_i;
        for(int i = 0; i < (array.length / 2); i++) {
            mirror_i = array.length - 1 - i;

            array[i] = array[i] + array[mirror_i];
            array[mirror_i] =  array[i] - array[mirror_i];
            array[i] = array[i] - array[mirror_i];
        }

        return array;
    }

}
