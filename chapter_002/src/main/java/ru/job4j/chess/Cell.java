package ru.job4j.chess;

/**
 * Ячейка
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Cell {
    int x;
    int y;

    /**
     * Конструктор
     * @param x ось Х
     * @param y ось Y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Формирование хэшкода
     * @return
     */
    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    /**
     * Сверяет текущий объект с объектом obj
     * @param obj сверяемый объект
     * @return true если объекты "равны"
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cell tmp = (Cell) obj;
        if ((this.x != tmp.x) || (this.y != tmp.y)) {
            return false;
        }
        return true;
    }
}
