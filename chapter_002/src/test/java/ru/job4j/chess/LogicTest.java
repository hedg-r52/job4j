package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static ru.job4j.chess.figures.Cell.*;

import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.figures.black.*;


public class LogicTest {

    @Test(expected = OccupiedWayException.class)
    public void whenMoveRookThroughPawnWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(H7));
        logic.add(new RookBlack(H8));
        logic.move(H8, H5);
    }

    @Test(expected = OccupiedWayException.class)
    public void whenMoveRookToPawnPlaceWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(H7));
        logic.add(new RookBlack(H8));
        logic.move(H8, H7);
    }

    @Test(expected = ImpossibleMoveException.class)
    public void whenMoveRookViaDiagonalWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new RookBlack(H8));
        logic.move(H8, D4);
    }

    @Test
    public void whenMoveRookViaFreeWayWhenMove() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new RookBlack(H8));
        boolean move = logic.move(H8, H4);
        assertThat(move, is(true));
    }

    @Test(expected = FigureNotFoundException.class)
    public void whenMoveEmptyCellThenException() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(H7));
        logic.move(H6, H4);

    }

    @Test
    public void whenMoveBishopViaFreeWayWhenMove() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new BishopBlack(F8));
        boolean move = logic.move(F8, B4);
        assertThat(move, is(true));
    }

    @Test(expected = ImpossibleMoveException.class)
    public void whenMoveBishopForwardWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new BishopBlack(F8));
        logic.move(F8, F4);
    }

    @Test(expected = OccupiedWayException.class)
    public void whenMoveBishopToPawnPlaceWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(G7));
        logic.add(new BishopBlack(F8));
        logic.move(F8, G7);
    }

    @Test(expected = OccupiedWayException.class)
    public void whenMoveBishopThroughPawnWhenError() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(E7));
        logic.add(new BishopBlack(F8));
        logic.move(F8, C5);
    }

    @Test
    public void whenMoveKnightThroughFigureThenTrue() throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Logic logic = new Logic();
        logic.add(new PawnBlack(F7));
        logic.add(new PawnBlack(F7));
        logic.add(new PawnBlack(G7));
        logic.add(new PawnBlack(H7));
        logic.add(new KnightBlack(G8));
        boolean move = logic.move(G8, F6);
        assertThat(move, is(true));
    }
}