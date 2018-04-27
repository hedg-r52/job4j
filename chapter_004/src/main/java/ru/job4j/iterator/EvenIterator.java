package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор четных чисел
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class EvenIterator implements Iterator {
    private final int[] numbers;
    private int count = 0;

    public EvenIterator(final int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean hasNext() {
        int i = count;
        while ((i < numbers.length) && (numbers[i] % 2 != 0)) {
            i++;
        }
        return (i < numbers.length);
    }

    @Override
    public Object next() {
        while ((count < numbers.length) && (numbers[count] % 2 != 0)) {
            count++;
        }
        if (count == numbers.length) {
            throw new NoSuchElementException();
        }
        return numbers[count++];
    }
}
