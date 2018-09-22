package ru.job4j.chess.exceptions;

/**
 * Исключение - Путь занят
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class OccupiedWayException extends Exception {
    /**
     * Конструктор
     */
    public OccupiedWayException() {
        super("The way occupied");
    }
}
