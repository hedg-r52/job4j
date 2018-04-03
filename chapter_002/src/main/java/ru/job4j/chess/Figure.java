package ru.job4j.chess;

/**
 * Фигура
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public abstract class Figure {
    final Cell position;

    /**
     * Конструктор
     * @param position позиция на доске
     */
    public Figure(Cell position) {
        this.position = position;
    }

    /**
     * "Прыгучесть" фигуры
     * @return возвращает для коня true(в переопределенном методе), для всех остальных false
     */
    public boolean isJumping() {
        return false;
    }

    /**
     * Путь прохождения фигуры
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return массив с ячейками пути
     * @throws ImpossibleMoveException возникает когда невозможно сделать ход
     */
    public abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    /**
     * Создание копии фигуры в указанной ячейке
     * @param dest ячейка куда скопируется фигура
     * @return Новая фигура
     */
    public abstract Figure copy(Cell dest);

    /**
     * Проверка возможности сделать ход
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return true если можно сделать ход
     */
    public abstract boolean canMove(Cell source, Cell dest);
}
