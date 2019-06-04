package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Собственная структура данных HashMap
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMap<K, V> implements Iterable<K> {

    private final static int DEFAULT_DATA_SIZE = 10;
    private final static float LOG_FACTOR_BOUND = 0.5f;

    private Pair<K, V>[] table;
    private int size;
    private int count = 0;
    private float logFactor = 0;

    public SimpleHashMap() {
        this.size = DEFAULT_DATA_SIZE;
        this.table = new Pair[this.size];
    }


    public boolean insert(K key, V value) {
        boolean result = false;
        if (logFactor > LOG_FACTOR_BOUND) {
            grow();
        }
        int i = hash(key);
        if (this.table[i] == null) {
            this.table[i] = new Pair<>(key, value);
            logFactor = (float) ++count / size;
            result = true;
        }
        return result;
    }

    public V get(K key) {
        int i = hash(key);
        return (this.table[i] != null && key.equals(this.table[i].key) ? this.table[i].value : null);
    }

    public boolean delete(K key) {
        boolean result = false;
        int i = hash(key);
        if (this.table[i] != null && key.equals(this.table[i].key)) {
            this.table[i] = null;
            count--;
            result = true;
        }
        return result;
    }

    private int hash(K key) {
        return key.hashCode() % size;
    }

    private void grow() {
        this.size = this.size * 2;
        Pair<K, V>[] newTable = new Pair[this.size];
        for (Pair<K, V> data : this.table) {
            if (data != null) {
                int i = hash((K) data.key);
                newTable[i] = data;
            }
        }
        this.table = newTable;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                boolean result = false;
                while (index < table.length) {
                    if (table[index] != null) {
                        result = true;
                        break;
                    }
                    index++;
                }
                return result;
            }

            @Override
            public K next() {
                while (index < table.length && table[index] == null) {
                    index++;
                }
                if (index >= table.length) {
                    throw new NoSuchElementException();
                }
                return (K) table[index++].key;
            }
        };
    }

    class Pair<K, V> {
        K key;
        V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
