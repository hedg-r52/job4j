package ru.job4j.set;

import ru.job4j.list.ArrayContainer;

import java.util.Iterator;

/**
 * Простое множество
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleSet<T> implements Iterable<T> {

    private ArrayContainer<T> list;
    private final static int DEFALUT_SIZE_LIST = 10;

    public SimpleSet() {
        this(DEFALUT_SIZE_LIST);
    }

    public SimpleSet(int size) {
        this.list = new ArrayContainer<>(size);
    }

    public void add(T value) {
        if (!list.contains(value)) {
            list.add(value);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
