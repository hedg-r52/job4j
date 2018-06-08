package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Контейнер на массиве
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ArrayContainer<E> implements Iterable<E> {
    @GuardedBy("this")
    private Object[] container;
    private final static int DEFAULT_CONTAINER_SIZE = 10;
    private int position;

    public ArrayContainer() {
        this(DEFAULT_CONTAINER_SIZE);
    }

    public ArrayContainer(int size) {
        this.container = new Object[size];
        position = 0;
    }

    public synchronized void add(E value) {
        if (position >= this.container.length) {
            growContainerSize();
        }
        this.container[position++] = value;
    }

    public synchronized E get(int index) {
        return (E) this.container[index];
    }

    public synchronized boolean contains(E value) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (value.equals(this.container[i])) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return (index < position);
            }

            @Override
            public synchronized E next() {
                return (E) container[index++];
            }
        };
    }

    private synchronized void growContainerSize() {
        this.container = Arrays.copyOf(this.container, this.container.length * 2);
    }
}
