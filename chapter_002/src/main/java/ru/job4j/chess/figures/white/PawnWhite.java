package ru.job4j.chess.figures.white;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.figures.Cell;
import ru.job4j.chess.figures.Figure;

/**
 * Class of Pawn figure white color.
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PawnWhite extends Figure {

    public PawnWhite(final Cell position) {
        super(position);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        return moveOnlyForwardOneStep(source, dest, false);
    }

    @Override
    public Figure copy(Cell dest) {
        return new PawnWhite(dest);
    }
}