package ru.job4j.jdbc.tracker;

public interface BiConEx<L, R> {
    void accept(L left, R right) throws Exception;
}
