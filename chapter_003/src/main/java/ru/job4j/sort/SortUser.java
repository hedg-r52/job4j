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
        Comparator<User> userComparator = new Comparator<User>() {
            public int compare(User o1, User o2) {
                return (o1.getName().length() - o2.getName().length());
            }
        };
        Collections.sort(list, userComparator);
        return list;
    }

    /**
     * Сортировка по всем полям
     * @param list входящий список
     * @return сортированный список
     */
    public List<User> sortByAllFields(List<User> list) {
        Comparator<User> userComparator = new Comparator<User>() {
            public int compare(User o1, User o2) {
                int result = o1.getName().compareTo(o2.getName());
                if (result == 0) {
                    result = o1.getAge() - o2.getAge();
                }
                return result;
            }
        };
        Collections.sort(list, userComparator);
        return list;
    }
}
