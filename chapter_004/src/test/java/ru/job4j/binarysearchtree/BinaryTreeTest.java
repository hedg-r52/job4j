package ru.job4j.binarysearchtree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса BinaryTest
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BinaryTreeTest {

    @Test
    public void whenAddThen() {
        BinaryTree<Integer> binaryTree = new BinaryTree<>(10);
        binaryTree.add(20);
        binaryTree.add(5);
        binaryTree.add(15);
        binaryTree.add(7);
        binaryTree.add(3);
        List<Integer> expected = Arrays.asList(3, 5, 7, 10, 15, 20);
        assertThat(binaryTree.asList(), is(expected));
    }
}