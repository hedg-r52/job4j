package ru.job4j.array;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class SortedArray {

    /**
     * объединение двух сортированных массивов в один сортированный массив
     * @param array1 первый сортированный массив
     * @param array2 второй сортированный массив
     * @return объединенный сортированный массив
     */
    public int[] join(int[] array1, int[] array2) {

        int[] result = new int[array1.length + array2.length];
        int i = 0;
        int j = 0;

        while ((i < array1.length) || (j < array2.length)) {

            // если первый массив меньше чем второй
            if (i == array1.length) {
                for (int m = j; m < array2.length; m++) {
                    result[i + m] = array2[m];
                }
                break;
            }

            // если второй массив меньше чем первого
            if (j == array2.length) {
                for (int m = i; m < array1.length; m++) {
                    result[j + m] = array1[m];
                }
                break;
            }

            if (array1[i] < array2[j]) {
                result[i + j] = array1[i];
                i++;
            } else {
                result[i + j] = array2[j];
                j++;
            }
        }

        return result;
    }

}
