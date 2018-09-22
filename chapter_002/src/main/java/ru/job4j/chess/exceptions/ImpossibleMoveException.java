package ru.job4j.chess.exceptions;

/**
 * Исключение - Невозможно сделать ход
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ImpossibleMoveException extends Exception {
    /**
     * Конструктор
     */
    public ImpossibleMoveException() {
        super("Impossible move");
    }
}
