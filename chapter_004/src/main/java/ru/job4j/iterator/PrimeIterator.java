package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор простых чисел
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PrimeIterator implements Iterator<Integer> {

    private final int[] ints;
    private int count = 0;

    public PrimeIterator(final int[] ints) {
        this.ints = ints;
    }

    @Override
    public boolean hasNext() {
        int i = count;
        while ((i < ints.length) && (!isSimpleNumber(ints[i]))) {
            i++;
        }
        return (i < ints.length);
    }

    @Override
    public Integer next() {
        while ((count < ints.length) && (!isSimpleNumber(ints[count]))) {
            count++;
        }
        if (count == ints.length) {
            throw new NoSuchElementException();
        }
        return ints[count++];
    }

    private boolean isSimpleNumber(int number) {
        boolean result = true;
        if (number > 1) {
            for (int i = number - 1; i > 1; i--) {
                if (number % i == 0) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
