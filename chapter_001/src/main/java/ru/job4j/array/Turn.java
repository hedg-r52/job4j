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
        int mirrorI;
        for (int i = 0; i < (array.length / 2); i++) {
            mirrorI = array.length - 1 - i;
            array[i] = array[i] + array[mirrorI];
            array[mirrorI] =  array[i] - array[mirrorI];
            array[i] = array[i] - array[mirrorI];
        }
        return array;
    }
}
