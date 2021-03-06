package ru.job4j.jdbc.tracker;

import java.util.List;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {
    private final String[] value;
    private int position;

    public StubInput(final String[] value) {
        this.value = value;
    }

    @Override
    public String ask(String question) {
        return this.value[this.position++];
    }

    @Override
    public int ask(String question, List<Integer> range) {
        boolean exist = false;
        int key = Integer.valueOf(this.ask(question));
        for (int value : range) {
            if (key == value) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out of menu range.");
        }
    }
}
