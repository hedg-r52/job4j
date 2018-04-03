package ru.job4j.chess;

/**
 * Доска
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Board {
    public final static int BOARD_SIZE = 8;
    Figure[] figures = new Figure[32];
    private int counter = 0;

    /**
     * Добавить фигуру на доску
     * @param figure фигура
     */
    public void add(Figure figure) {
        figures[counter++] = figure;
    }

    /**
     * Переместить фигуру
     * @param source ячейка откуда делается ход
     * @param dest ячейка куда делается ход
     * @return true если ячейка перемещена
     * @throws ImpossibleMoveException Невозможно сделать ход
     * @throws OccupiedWayException На пути хода стоит фигура
     * @throws FigureNotFoundException Фигура в ячейке откуда делается ход не найдена
     */
    public boolean move(Cell source, Cell dest)
            throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        int index = getIndexFigureInCell(source);
        if (index == -1) {
            throw new FigureNotFoundException();
        }
        if (!figures[index].canMove(source, dest)) {
            throw new ImpossibleMoveException();
        }
        for (Cell cell : figures[index].way(source, dest)) {
            if (!isEmpty(cell)) {
                throw new OccupiedWayException();
            }
        }
        figures[index] = figures[index].copy(dest);
        return true;
    }

    /**
     * Получить индекс фигуры
     * @param cell ячейка для поиска
     * @return Возвращает индекс фигуры или -1 если фигура не найдена
     */
    protected int getIndexFigureInCell(Cell cell) {
        int result = -1;
        for (int i = 0; i < counter; i++) {
            if (cell.equals(figures[i].position)) {
                result = i;
                break;
            }
        }
        return result;
    }

    /**
     * Проверка пустая ли ячейка
     * @param checkedCell проверяемая ячейка
     * @return true если ячейка пустая (не занята фигурой)
     */
    private boolean isEmpty(Cell checkedCell) {
        boolean result = true;
        for (int i = 0; i < counter; i++) {
            if (checkedCell.equals(figures[i].position)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
