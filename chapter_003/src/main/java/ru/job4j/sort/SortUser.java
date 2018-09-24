package ru.job4j.sort;

import java.util.*;

/**
 * Сортировка пользователей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class SortUser {

    /**
     * Сортировка с помощью Comparable
     * @param list входящий список
     * @return сортированный список(набор)
     */
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }

    /**
     * Сортировка по длине имени
     * @param list входящий список
     * @return сортированный список
     */
    public List<User> sortNameLength(List<User> list) {
        Comparator<User> userComparator = (o1, o2) -> (o1.getName().length() - o2.getName().length());
        list.sort(userComparator);
        return list;
    }

    /**
     * Сортировка по всем полям
     * @param list входящий список
     * @return сортированный список
     */
    public List<User> sortByAllFields(List<User> list) {
        Comparator<User> userComparator = Comparator.comparing(User::getName).thenComparingInt(User::getAge);
        list.sort(userComparator);
        return list;
    }
}
