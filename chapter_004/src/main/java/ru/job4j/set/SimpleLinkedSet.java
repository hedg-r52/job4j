package ru.job4j.set;

import ru.job4j.list.LinkedContainer;

import java.util.Iterator;

/**
 * Простое множество на связанных списках
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleLinkedSet<T> implements Iterable<T> {
    private LinkedContainer<T> list;

    public SimpleLinkedSet() {
        this.list = new LinkedContainer<>();
    }

    public void add(T value) {
        boolean isExist = false;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (value.equals(it.next())) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            list.add(value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
