package ru.job4j.list;

/**
 * Простой стек
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStack<T> {
    private LinkedContainer<T> container;

    public SimpleStack() {
        this.container = new LinkedContainer<>();
    }

    public T poll() {
        return this.container.delete(0);
    }

    public void push(T value) {
        this.container.addFirst(value);
    }
}
