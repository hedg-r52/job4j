package ru.job4j.jdbc.tracker;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface UserAction {
    int key();
    void execute(Input input, Tracker tracker);
    String info();
}
