package ru.job4j.segment;

import java.util.function.Predicate;

/**
 * Отрезок
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Segment {
    private int begin;
    private int end;

    public Segment(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public boolean isCrossed(Segment segment) {
        return isCrossed.test(segment);
    }

    public Predicate<Segment> isCrossed = new Predicate<Segment>() {
        @Override
        public boolean test(Segment segment) {
            return ((segment.begin > begin && segment.begin < end)
                    || (segment.end > begin && segment.end < end)
                    || (segment.begin <= begin && segment.end >= end));
        }
    };


}
