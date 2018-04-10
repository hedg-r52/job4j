package ru.job4j.chess;

import java.util.Arrays;

/**
 * Слон
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Bishop extends Figure {

    /**
     * Конструктор
     * @param position ячейка в которой будет создана фигура Слон
     */
    public Bishop(Cell position) {
        super(position);
    }

    /**
     * Путь прохождения фигуры
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return массив с ячейками пути
     * @throws ImpossibleMoveException возникает когда невозможно сделать ход
     */
    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        if (dest.equals(source)) {
            throw new ImpossibleMoveException();
        }
        int moves = Math.abs(source.x - dest.x);
        Cell[] result = new Cell[moves];
        int count;
        int stepX = (source.x > dest.x ? -1 : 1);
        int stepY = (source.y > dest.y ? -1 : 1);
        int x = source.x + stepX;
        int y = source.y + stepY;
        for (count = 0; count < moves; count++) {
            result[count] = new Cell(x, y);
            x = x + stepX;
            y = y + stepY;
        }
        return Arrays.copyOf(result, count);
    }

    /**
     * Создание копии фигуры в указанной ячейке
     * @param dest ячейка куда скопируется фигура
     * @return Новая фигура
     */
    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest);
    }

    /**
     * Проверка возможности сделать ход
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return true если можно сделать ход
     */
    @Override
    public boolean canMove(Cell source, Cell dest) throws ImpossibleMoveException {
        if (inBounds(source.x) && inBounds(source.y)
                && inBounds(dest.x) && inBounds(dest.y)) {
            Cell[] way = way(source, dest);
            return  cellOnTheWay(dest, way);
        } else {
            return false;
        }
    }

    /**
     * Ячейка входит в массив ячеек
     * Используется для проверки что ход будет сделан в ячейку из возможных ходов
     * @param dest ячейка куда делается ход
     * @param map массив ячеек куда можно сделать ход
     * @return true если ячейка входит в массив ячеек
     */
    private boolean cellOnTheWay(Cell dest, Cell[] map) {
        boolean result = false;
        for (Cell cell : map) {
            if (dest.equals(cell)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean inBounds(int i) {
        return (i >= 0 && i < Board.BOARD_SIZE);
    }

}
