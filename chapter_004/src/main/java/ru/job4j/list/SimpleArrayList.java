package ru.job4j.list;

/**
 * Класс SimpleArrayList
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayList<E> {
    private int size;
    private Node<E> first;

    public void add(E data) {
        Node<E> newLink = new Node<>(data);
        newLink.next = this.first;
        this.first = newLink;
        this.size++;
    }

    public E delete() {
        Node<E> deleting = this.first;
        this.first = deleting.next;
        this.size--;
        return deleting.data;
    }

    public E delete(int index) {
        if (index >= size) {
            throw new UnsupportedOperationException();
        }
        Node<E> prev = this.first;
        Node<E> result = this.first;
        for (int i = 0;; i++) {
            if (i == index) {
                if (i == 0) {
                    this.first = result.next;
                } else {
                    prev.next = result.next;
                }
                break;
            }
            prev = result;
            result = result.next;
        }
        return result.data;
    }

    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.data;
    }

    public int getSize() {
        return this.size;
    }

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
        }
    }
}
