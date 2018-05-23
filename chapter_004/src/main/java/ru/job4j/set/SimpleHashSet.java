package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Коллекция типа Set на базе хэш-таблицы
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSet<E> implements Iterable<E> {

    private final static int DEFAULT_DATA_SIZE = 10;
    private final static float LOG_FACTOR_BOUND = 0.5f;

    private Object[] values;
    private int arraySize;
    private int count = 0;
    private float logFactor = 0;

    public SimpleHashSet() {
        this.arraySize = DEFAULT_DATA_SIZE;
        this.values = new Object[this.arraySize];

    }

    public boolean add(E value) {
        boolean result = false;
        if (logFactor > LOG_FACTOR_BOUND) {
            grow();
        }
        int i = hash(value);
        if (this.values[i] == null) {
            this.values[i] = value;
            logFactor = (float) ++count / arraySize;
            result = true;
        }
        return result;
    }

    public boolean contains(E value) {
        int i = hash(value);
        return value.equals(this.values[i]);
    }

    public boolean remove(E value) {
        boolean result = false;
        int i = hash(value);
        if (this.values[i] != null) {
            this.values[i] = null;
            count--;
            result = true;
        }
        return result;
    }

    private int hash(E value) {
        return value.hashCode() % arraySize;
    }

    private void grow() {
        this.arraySize = this.arraySize * 2;
        Object[] newValues = new Object[this.arraySize];
        for (Object data : this.values) {
            if (data != null) {
                int i = hash((E) data);
                newValues[i] = data;
            }
        }
        this.values = newValues;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                boolean result = false;
                while (index < values.length) {
                    if (values[index] != null) {
                        result = true;
                        break;
                    }
                    index++;
                }
                return result;
            }

            @Override
            public E next() {
                while (index < values.length && values[index] == null) {
                    index++;
                }
                if (index >= values.length) {
                    throw new NoSuchElementException();
                }
                return (E) values[index++];
            }
        };
    }
}
