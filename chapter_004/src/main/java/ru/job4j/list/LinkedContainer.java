package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Контейнер на связанных списках
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class LinkedContainer<E> implements Iterable<E> {
    private int size;
    private int modCount = 0;
    private Node<E> first;
    private Node<E> last;

    public LinkedContainer() {
        this.size = 0;
    }

    public synchronized void add(E value) {
        Node<E> newNode = new Node<>(value);
        if (size == 0) {
            this.first = newNode;
        } else {
            this.last.next = newNode;
        }
        this.last = newNode;
        this.size++;
        this.modCount++;
    }

    public synchronized void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = this.first;
        this.first = newNode;
        this.size++;

    }

    public synchronized E get(int index) {
        Node<E> result = first;
        checkIndex(index);
        for (int i = 0;; i++) {
            if (i == index) {
                break;
            }
            result = result.next;
        }
        return result.data;
    }

    public synchronized E delete(int index) {
        Node<E> result = first;
        Node<E> prev = first;
        checkIndex(index);
        for (int i = 0;; i++) {
            if (i == index) {
                if (i == 0) {
                    this.first = result.next;
                } else {
                    prev.next = result.next;
                }
                this.size--;
                break;
            }
            prev = result;
            result = result.next;
        }
        return result.data;
    }

    private int getSize() {
        return this.size;
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public boolean contains(E value) {
        boolean result = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (value.equals(it.next())) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = first;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return (current != null);
            }

            @Override
            public E next() {
                checkModify();
                E result = current.data;
                current = current.next;
                return result;
            }

            private void checkModify() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @ThreadSafe
    private static class Node<E> {
        @GuardedBy("this")
        E data;
        @GuardedBy("this")
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}
