package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Node
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NodeTest {

    Node<Integer> first;
    Node<Integer> second;
    Node<Integer> third;
    Node<Integer> four;

    @Before
    public void setUp() {
        first = new Node(1);
        second = new Node(2);
        third = new Node(3);
        four = new Node(4);
    }

    @Test
    public void  whenHasCycleOnFourELementThenResultTrue() {
        first.next = second;
        second.next = third;
        third.next = four;
        four.next = first;
        assertThat(first.hasCycle(), is(true));

    }

    @Test
    public void  whenHasCycleOnSecondElementThenResultTrue() {
        first.next = second;
        second.next = first;
        third.next = four;
        assertThat(first.hasCycle(), is(true));
    }

    @Test
    public void  whenHasNotCycleThenResultFalse() {
        first.next = second;
        second.next = third;
        third.next = four;
        assertThat(first.hasCycle(), is(false));
    }

}