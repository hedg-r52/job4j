package ru.job4j.list;

import ru.job4j.generic.SimpleArray;

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

    public static boolean hasCycle(Node first) {
        boolean result = false;
        Node slow = first;
        Node fast = first;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                result = true;
                break;
            }
        }
        return result;
    }
}
