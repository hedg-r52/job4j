package ru.job4j.chess;

/**
 * Исключение - Фигура не найдена
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class FigureNotFoundException extends Exception {
    /**
     * Конструктор
     */
    public FigureNotFoundException() {
        super("Figure isn't found!");
    }
}
