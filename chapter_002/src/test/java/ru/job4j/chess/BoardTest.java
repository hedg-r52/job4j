package ru.job4j.chess;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void whenFiguresAddAtOneOneAndFourFiveThenFigureExistAtCellFourFive() {
        Board board = new Board();
        board.add(new Bishop(new Cell(1, 1)));
        board.add(new Bishop(new Cell(4, 5)));
        assertThat(board.getIndexFigureInCell(new Cell(4, 5)), is(1));
    }

    @Test
    public void whenFiguresAddAtOneOneAndFourFiveThenFigureNotExistAtCellFourFour() {
        Board board = new Board();
        board.add(new Bishop(new Cell(1, 1)));
        board.add(new Bishop(new Cell(4, 5)));
        assertThat(board.getIndexFigureInCell(new Cell(4, 4)), is(-1));
    }

    @Test(expected = FigureNotFoundException.class)
    public void whenAttemptedMoveFigureFromEmptyCellThenGetFigureNotFoundException()
            throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Board board = new Board();
        board.move(new Cell(1, 1), new Cell(2, 2));
    }

    @Test(expected = ImpossibleMoveException.class)
    public void whenAttemptedMoveFigureOnOccupiedCellThenImpossibleMoveException()
            throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Board board = new Board();
        Cell cellMoveFrom = new Cell(1, 1);
        Cell cellOutBoard = new Cell(Board.BOARD_SIZE, Board.BOARD_SIZE);
        board.add(new Bishop(cellMoveFrom));
        board.add(new Bishop(cellOutBoard));
        board.move(cellMoveFrom, cellOutBoard);
    }

    @Test(expected = OccupiedWayException.class)
    public void whenAttemptedMoveFigureOnOccupiedWayThenOccupiedWayException()
            throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Board board = new Board();
        Cell cellMoveFrom = new Cell(1, 1);
        Cell cellOccupied = new Cell(3, 3);
        Cell cellMoveTo = new Cell(4, 4);
        board.add(new Bishop(cellMoveFrom));
        board.add(new Bishop(cellOccupied));
        board.move(cellMoveFrom, cellMoveTo);
    }

    @Test
    public void whenMoveFigureFromOneOneOnFourFourThenFigureNotExistOneOneAndExistFourFour()
            throws OccupiedWayException, ImpossibleMoveException, FigureNotFoundException {
        Board board = new Board();
        Cell source = new Cell(1, 1);
        Cell dest = new Cell(4, 4);
        board.add(new Bishop(source));
        assertThat(board.getIndexFigureInCell(source), is(0));
        assertThat(board.getIndexFigureInCell(dest), is(-1));
        board.move(source, dest);
        assertThat(board.getIndexFigureInCell(source), is(-1));
        assertThat(board.getIndexFigureInCell(dest), is(0));
    }
}