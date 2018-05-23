package ru.job4j.binarysearchtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Двоичное дерево поиска
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BinaryTree<E extends Comparable<E>> {

    private Node<E> root;

    public BinaryTree(E value) {
        this.root = new Node<>(value);
    }

    public void add(E e) {
        Node<E> newNode = new Node<E>(e);
        if (this.root == null) {
            this.root = newNode;
        } else {
            addFactorial(this.root, newNode);
        }
    }

    private void addFactorial(Node<E> apex, Node<E> newNode) {
        if (newNode.data.compareTo(apex.data) > 0) {
            if (apex.right == null) {
                apex.right = newNode;
            } else {
                addFactorial(apex.right, newNode);
            }
        } else {
            if (apex.left == null) {
                apex.left = newNode;
            } else {
                addFactorial(apex.left, newNode);
            }
        }
    }

    public List<E> asList() {
        List<E> result = new ArrayList<>();
        asListFactorial(this.root, result);
        return result;
    }

    private void asListFactorial(Node<E> apex, List<E> list) {
        if (apex.left != null) {
            asListFactorial(apex.left, list);
        }
        list.add(apex.data);
        if (apex.right != null) {
            asListFactorial(apex.right, list);
        }
    }

    class Node<E> {
        public E data;
        public Node<E> left;
        public Node<E> right;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

    }
}
