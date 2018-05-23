package ru.job4j.list;

/**
 * Простая очередь
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueue<T> {
    private LinkedContainer<T> container;

    public SimpleQueue() {
        this.container = new LinkedContainer<>();
    }

    public T poll() {
        return this.container.delete(0);
    }

    public void push(T value) {
        this.container.add(value);
    }
}
