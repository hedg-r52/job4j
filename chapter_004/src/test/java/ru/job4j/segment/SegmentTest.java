package ru.job4j.segment;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SegmentTest {

    @Test
    public void whenFirstCutTailCrossSecondCut() {
        Segment first = new Segment(1, 5);
        Segment second = new Segment(3, 7);
        assertThat(first.isCrossed(second), is(true));
    }

    @Test
    public void whenFirstCutHeadCrossSecondCut() {
        Segment first = new Segment(5, 9);
        Segment second = new Segment(3, 7);
        assertThat(first.isCrossed(second), is(true));
    }

    @Test
    public void whenFirstCutIntoSecondCut() {
        Segment first = new Segment(3, 5);
        Segment second = new Segment(1, 7);
        assertThat(first.isCrossed(second), is(true));
    }

    @Test
    public void whenFirstCutNotCrossSecondCut() {
        Segment first = new Segment(1, 3);
        Segment second = new Segment(5, 7);
        assertThat(first.isCrossed(second), is(false));
    }
}