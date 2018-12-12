package ru.job4j.jdbc.tracker;

/**
 * Function interface. It take a single param.
 * @param <T> type.
 */
public interface ConEx<T> {
    /**
     * Accept arg.
     * @param param arg
     * @throws Exception possible exception.
     */
    void accept(T param) throws Exception;
}
