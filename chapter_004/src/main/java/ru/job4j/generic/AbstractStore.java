package ru.job4j.generic;

import java.util.Iterator;

/**
 * "Абстрактное" хранилище
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T>  {
    private SimpleArray<T> store;
    private int position = 0;
    private static final int DEFAULT_STORE_SIZE = 10;

    AbstractStore() {
        this(DEFAULT_STORE_SIZE);
    }

    AbstractStore(int size) {
        this.store = new SimpleArray<>(size);
    }

    @Override
    public void add(T model) {
        this.store.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        Iterator<T> it = this.store.iterator();
        while (it.hasNext()) {
            T value = it.next();
            if (id.equals(value.getId())) {
                result = true;
                value = model;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        Iterator<T> it = this.store.iterator();
        for (int i = 0; it.hasNext(); i++) {
            T value = it.next();
            if (id.equals(value.getId())) {
                this.store.delete(i);
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public T findById(String id) {
        T result = null;
        Iterator<T> it = this.store.iterator();
        for (int i = 0; it.hasNext(); i++) {
            T value = it.next();
            if (id.equals(value.getId())) {
                result = value;
                break;
            }
        }
        return result;
    }
}
