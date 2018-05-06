package ru.job4j.tree;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Дерево
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    private Node<E> root;

    public Tree(E value) {
        this.root = new Node<>(value);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Node<E> finded = findBy(parent);
        if (finded != null) {
            finded.add(new Node(child));
            result = true;
        }
        return result;
    }

    @Override
    public Node<E> findBy(E value) {
        return findFact(this.root, value);
    }

    public boolean isBinary() {
        return isBinaryFact(this.root);
    }

    private boolean isBinaryFact(Node<E> branch) {
        boolean result = true;
        if (branch.leaves().size() > 2) {
            result = false;
        } else {
            for (Node<E> child : branch.leaves()) {
                result = isBinaryFact(child);
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }

    private Node<E> findFact(Node<E> branch, E value) {
        Node<E> result = null;
        if (branch.eqValue((value))) {
            result = branch;
        } else {
            for (Node<E> child : branch.leaves()) {
                result = findFact(child, value);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };
    }
}
