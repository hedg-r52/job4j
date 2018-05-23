package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Конвертер
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Converter {
    /**
     * Конвертирование вложенных итераторов в один итератор
     * @param it итераторы итераторов целых
     * @return итератор целых
     */
    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> currentInnerIt = it.next();

            @Override
            public boolean hasNext() {
                boolean result = true;
                if (!currentInnerIt.hasNext()) {
                    result = false;
                    while (it.hasNext()) {
                        currentInnerIt = it.next();
                        if (currentInnerIt.hasNext()) {
                            result = true;
                            break;
                        }
                    }
                }
                return result;
            }

            @Override
            public Integer next() {
                Integer result = null;
                if (!currentInnerIt.hasNext() && !it.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (currentInnerIt.hasNext()) {
                    result = currentInnerIt.next();
                } else {
                    while (it.hasNext()) {
                        currentInnerIt = it.next();
                        if (currentInnerIt.hasNext()) {
                            result = currentInnerIt.next();
                            break;
                        }
                    }
                }
                return result;
            }
        };
    }
}