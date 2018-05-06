package ru.job4j.tree;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование Tree
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TreeTest {

    Tree<Integer> tree;

    @Before
    public void setUp() throws Exception {
        tree = new Tree<>(1);
    }

    @Test
    public void when6ElFindLastThen6() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6) != null,
                is(true)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        tree.add(1, 2);
        assertThat(
                tree.findBy(7) != null,
                is(false)
        );
    }

    @Test
    public void whenCheckBinaryTreeThenTrue() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(3, 6);
        tree.add(3, 7);
        tree.add(7, 8);
        assertThat(tree.isBinary(), is(true));
    }

    @Test
    public void whenCheckNotBinaryTreeThenFalse() {
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(2, 5);
        tree.add(3, 6);
        tree.add(3, 7);
        tree.add(3, 8);
        assertThat(tree.isBinary(), is(false));
    }
}