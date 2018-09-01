package ru.job4j.jdbc.tracker;

import java.util.List;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface Input {
    String ask(String question);
    int ask(String question, List<Integer> range);
}
