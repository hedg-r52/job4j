package ru.job4j.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * TODO description
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        char[] str1 = left.toCharArray();
        char[] str2 = right.toCharArray();
        int min = Math.min(str1.length, str2.length);
        int result = 0;
        for (int i = 0; i < min; i++) {
            if (str1[i] != str2[i]) {
               result = Integer.compare(str1[i], str2[i]);
               break;
            }
        }
        if (result == 0 && str1.length != str2.length) {
            result = str1.length - str2.length;
        }
        return result;
    }
}
