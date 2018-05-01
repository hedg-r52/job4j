package ru.job4j.list;

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
    Node<E> first;
    Node<E> last;

    public LinkedContainer() {
        this.size = 0;
    }

    public void add(E value) {
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

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = this.first;
        this.first = newNode;
        this.size++;

    }

    public E get(int index) {
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

    public E delete(int index) {
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

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}
