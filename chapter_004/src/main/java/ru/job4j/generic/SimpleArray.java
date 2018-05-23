package ru.job4j.generic;

import java.util.Iterator;

/**
 * Контейнер
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArray<T> implements Iterable<T> {
    Object[] values;
    private int position;

    public SimpleArray(int count) {
        this.values = new Object[count];
        this.position = 0;
    }

    public void add(T model) {
        this.values[position++] = model;
    }

    public void set(int index, T model) {
        if (index < position) {
            this.values[index] = model;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void delete(int index) {
        if (index < position) {
            for (int i = index; i < position - 1; i++) {
                this.values[i] = this.values[i + 1];
            }
            this.values[position--] = null;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public T get(int index) {
        if (index >= position) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (T) this.values[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < position;
            }

            @Override
            public T next() {
                return (T) values[index++];
            }
        };
    }
}
