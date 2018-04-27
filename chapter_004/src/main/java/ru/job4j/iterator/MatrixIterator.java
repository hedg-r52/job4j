package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO description
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MatrixIterator implements Iterator {
    private final int[][] values;
    private int countI = 0;
    private int countJ = 0;

    public MatrixIterator(int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return (countI < values.length) && (countJ < values[countI].length);
    }

    @Override
    public Object next() {
        int result;
        if (countI < this.values.length) {
            result = this.values[countI][countJ++];
            if (countJ == this.values[countI].length) {
                countI++;
                countJ = 0;
            }
        } else {
            throw new NoSuchElementException();
        }
        return result;
    }
}
