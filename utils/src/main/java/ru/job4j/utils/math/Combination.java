package ru.job4j.utils.math;

import java.util.*;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Combination {
    /**
     * Generate unique sequence of values. Object from value may appear once.
     * @param values input values
     * @param <T> type of values
     * @return list of list values
     */
    public static <T> List<List<T>> generate(List<T> values) {
        List<List<T>> combination = new LinkedList<>();
        if (values.size() > 2) {
            T actual = values.iterator().next();
            List<T> subSet = new LinkedList<>(values);
            subSet.remove(actual);
            List<List<T>> subSetCombination = generate(subSet);
            for (List<T> set : subSetCombination) {
                for (int i = 0; i <= set.size(); i++) {
                    List<T> newSet = new LinkedList<>(set);
                    newSet.add(i, actual);
                    combination.add(newSet);
                }
            }
        } else if (values.size() == 2) {
            combination.add(Arrays.asList(values.get(0), values.get(1)));
            combination.add(Arrays.asList(values.get(1), values.get(0)));
        }
        return combination;
    }

    /**
     * Generate unique sequence of value. Every with every. Objects from values may appear more than once.
     * @param values input values
     * @param count limit of objects
     * @param <T> type of values
     * @return list of list values
     */
    public static <T> List<List<T>> generateEveryWithEvery(List<T> values, int count) {
        List<List<T>> combination = new LinkedList<>();
        if (count == 1) {
            for (T value : values) {
                combination.add(Arrays.asList(value));
            }
        } else {
            for (T value : values) {
                for (List<T> set : generateEveryWithEvery(values, count - 1)) {
                    List<T> newSet = new LinkedList<>(set);
                    newSet.add(0, value);
                    combination.add(newSet);
                }
            }
        }
        return combination;
    }
}
