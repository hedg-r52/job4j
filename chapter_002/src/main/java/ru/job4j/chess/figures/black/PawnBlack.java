package ru.job4j.chess.figures.black;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.figures.Cell;
import ru.job4j.chess.figures.Figure;

/**
 * Class of Pawn figure black color.
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PawnBlack extends Figure {

    public PawnBlack(final Cell position) {
        super(position);
    }


    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        return moveOnlyForwardOneStep(source, dest, true);
    }

    @Override
    public Figure copy(Cell dest) {
        return new PawnBlack(dest);
    }
}