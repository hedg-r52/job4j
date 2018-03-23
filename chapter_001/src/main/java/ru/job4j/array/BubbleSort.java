package ru.job4j.array;

/**
 * @author Andrei Soloviev (hedg.r52)
 * @version $Id$
 * @since 0.1
 */
public class BubbleSort {

    /**
     * Сортировка "пузырьком"
     * @param array входящий массив
     * @return сортированный масси
     */
    public int[] sort(int[] array) {

        boolean notSwap = true;
        for (int i = 0; i < array.length - 1; i++) {

            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    array[j] = array[j] + array[j + 1];
                    array[j + 1] = array[j] - array[j + 1];
                    array[j] = array[j] - array[j + 1];

                    notSwap = false;
                }
            }
            if (notSwap) {
                break;
            }
        }

        return array;
    }
}
