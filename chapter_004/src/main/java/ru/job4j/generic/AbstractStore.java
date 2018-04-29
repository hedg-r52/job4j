package ru.job4j.generic;

/**
 * "Абстрактное" хранилище
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T>  {
    protected T[] store;
    protected int position = 0;

    @Override
    public void add(T model) {
        this.store[position++] = model;
    }

    @Override
    public boolean replace(String id, T model) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (id.equals(this.store[i].getId())) {
                result = true;
                this.store[i] = model;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result = false;
        for (int i = 0; i < position; i++) {
            if (result) {
                this.store[i - 1] = this.store[i];
            } else {
                result = (id.equals(this.store[i].getId()));
            }
        }
        if (result) {
            this.store[position--] = null;
        }
        return result;
    }

    @Override
    public T findById(String id) {
        T result = null;
        for (int i = 0; i < position; i++) {
            if (id.equals(this.store[i].getId())) {
                result = this.store[i];
                break;
            }
        }
        return result;
    }
}
