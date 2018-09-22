package ru.job4j.chess;

import ru.job4j.chess.exceptions.*;
import ru.job4j.chess.figures.Cell;
import ru.job4j.chess.figures.Figure;
import java.util.Optional;

/**
 * Logic for chess
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Logic {
    private final Figure[] figures = new Figure[32];
    private int index = 0;

    public void add(Figure figure) {
        this.figures[this.index++] = figure;
    }

    public boolean move(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedWayException,
            FigureNotFoundException {
        boolean rst = false;
        Optional<Integer> index = findBy(source);
        Optional<Integer> target = findBy(dest);
        if (!index.isPresent()) {
            throw new FigureNotFoundException();
        }
        Cell[] steps = this.figures[index.get()].way(source, dest);
        for (Cell cell : steps) {
            Optional<Integer> empty = findBy(cell);
            if (empty.isPresent() || target.isPresent()) {
                throw new OccupiedWayException();
            }
        }
        if (steps.length > 0 && steps[steps.length - 1].equals(dest)) {
            rst = true;
            this.figures[index.get()] = this.figures[index.get()].copy(dest);
        }
        return rst;
    }

    public void clean() {
        for (int position = 0; position != this.figures.length; position++) {
            this.figures[position] = null;
        }
        this.index = 0;
    }

    private Optional<Integer> findBy(Cell cell) {
        for (int index = 0; index != this.figures.length; index++) {
            if (this.figures[index] != null && this.figures[index].position().equals(cell)) {
                return Optional.of(index);
            }
        }
        return Optional.empty();
    }
}
