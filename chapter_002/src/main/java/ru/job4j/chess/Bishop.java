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
        Cell[] result = new Cell[32];
        int count = 0;
        if (dest.equals(source)) {
            throw new ImpossibleMoveException();
        }
        Cell[] map = getPossibleMoves(source, dest);
        if (!cellAtMap(dest, map)) {
            throw new ImpossibleMoveException();
        }
        int stepX = (source.x > dest.x ? -1 : 1);
        int stepY = (source.y > dest.y ? -1 : 1);
        int x = source.x + stepX;
        int y = source.y + stepY;
        while ((x >= 0 && x < Board.BOARD_SIZE)
                && (y >= 0 && y < Board.BOARD_SIZE)) {
            result[count++] = new Cell(x, y);
            x = x + stepX;
            y = y + stepY;
        }
        if (result.length == 0) {
            throw new ImpossibleMoveException();
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
    public boolean canMove(Cell source, Cell dest) {
        Cell[] map = getPossibleMoves(source, dest);
        return  cellAtMap(dest, map);
    }

    /**
     * Получить массив (карта) всех возможных ходов из ячейки source
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return массив всех возможных ходов
     */
    private Cell[] getPossibleMoves(Cell source, Cell dest) {
        int count = 0;
        Cell[] result = new Cell[32];
        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            int x = source.x - source.y + y;
            if (x == source.x && y == source.y) {
                continue;
            }
            if (x >= 0 && x < Board.BOARD_SIZE) {
                result[count++] = new Cell(x, y);
            }
        }
        for (int y = 0; y < Board.BOARD_SIZE; y++) {
            int x = source.x + source.y - y;
            if (x == source.x && y == source.y) {
                continue;
            }
            if (x >= 0 && x < Board.BOARD_SIZE) {
                result[count++] = new Cell(x, y);
            }
        }
        return Arrays.copyOf(result, count);
    }

    /**
     * Ячейка входит в массив ячеек
     * Используется для проверки что ход будет сделан в ячейку из возможных ходов
     * @param dest ячейка куда делается ход
     * @param map массив ячеек куда можно сделать ход
     * @return true если ячейка входит в массив ячеек
     */
    private boolean cellAtMap(Cell dest, Cell[] map) {
        boolean result = false;
        for (Cell cell : map) {
            if (dest.equals(cell)) {
                result = true;
                break;
            }
        }
        return result;
    }


}
