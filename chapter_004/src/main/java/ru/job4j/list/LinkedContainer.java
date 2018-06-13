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
@ThreadSafe
public class LinkedContainer<E> implements Iterable<E> {
    private final Object lock = new Object();
    @GuardedBy("lock")
    private int size;
    @GuardedBy("lock")
    private int modCount = 0;
    //@GuardedBy("lock")
    private Node<E> first;
    //@GuardedBy("lock")
    private Node<E> last;

    public LinkedContainer() {
        this.size = 0;
    }

    public void add(E value) {
        synchronized (lock) {
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
    }

    public void addFirst(E value) {
        synchronized (lock) {
            Node<E> newNode = new Node<>(value);
            newNode.next = this.first;
            this.first = newNode;
            this.size++;
        }
    }

    public E get(int index) {
        synchronized (lock) {
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
    }

    public synchronized E delete(int index) {
        synchronized (lock) {
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
    }

    private int getSize() {
        synchronized (lock) {
            return this.size;
        }
    }

    private void checkIndex(int index) {
        synchronized (lock) {
            if (index >= size) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }


    @Override
    @GuardedBy("lock")
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
                synchronized (lock) {
                    checkModify();
                    E result = current.data;
                    current = current.next;
                    return result;
                }
            }

            private void checkModify() {
                synchronized (lock) {
                    if (expectedModCount != modCount) {
                        throw new ConcurrentModificationException();
                    }
                }
            }
        };
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

    @ThreadSafe
    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}
