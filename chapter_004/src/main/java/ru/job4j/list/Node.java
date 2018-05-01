package ru.job4j.list;

import ru.job4j.generic.SimpleArray;

import java.util.Iterator;

/**
 * Узел списка
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Node<T> {
    T value;
    Node<T> next;
    private SimpleArray<Node<T>> storage;

    public Node(T value) {
        this.value = value;
    }

    public boolean hasCycle() {
        boolean result = false;
        Node current = this;
        this.storage = new SimpleArray<>(10);
        while (current.next != null) {
            Iterator<Node<T>> it = storage.iterator();
            while (it.hasNext()) {
                Node<T> next = it.next();
                if (current.equals(next)) {
                    result = true;
                    break;
                }
            }
            if (result) {
                break;
            }
            this.storage.add(current);
            current = current.next;
        }
        return result;
    }
}
