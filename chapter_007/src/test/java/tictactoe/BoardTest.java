package tictactoe;

/**
 * Test for Board
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
import org.junit.Test;
import tictactoe.board.SimpleBoard;
import tictactoe.symbols.DoubleSymbols;
import tictactoe.symbols.SingleSymbols;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void whenDrawDoubledGridWithValuesShouldDrawGridAndXAtOneOneAndOAtTwoTwoCells() {
        final String ln = System.getProperty("line.separator");
        String expected = "╔═══╦═══╦═══╗" + ln
                        + "║   ║   ║   ║" + ln
                        + "╠═══╬═══╬═══╣" + ln
                        + "║   ║ X ║   ║" + ln
                        + "╠═══╬═══╬═══╣" + ln
                        + "║   ║   ║ O ║" + ln
                        + "╚═══╩═══╩═══╝" + ln;
        SimpleBoard board = new SimpleBoard(new DoubleSymbols());
        board.changeSymbolAt(Mark.X, 1, 1);
        board.changeSymbolAt(Mark.O, 2, 2);
        assertThat(board.getGridWithValues(), is(expected));
    }

    @Test
    public void whenDrawSingledGridWithValuesShouldDrawGridAndXAtOneOneAndOAtTwoTwoCells() {
        final String ln = System.getProperty("line.separator");
        String expected = "┌───┬───┬───┐" + ln
                        + "│   │   │   │" + ln
                        + "├───┼───┼───┤" + ln
                        + "│   │ X │   │" + ln
                        + "├───┼───┼───┤" + ln
                        + "│   │   │ O │" + ln
                        + "└───┴───┴───┘" + ln;
        SimpleBoard board = new SimpleBoard(new SingleSymbols());
        board.changeSymbolAt(Mark.X, 1, 1);
        board.changeSymbolAt(Mark.O, 2, 2);
        assertThat(board.getGridWithValues(), is(expected));
    }

    @Test
    public void whenBoardHasEmptyFieldsIsFilledShouldReturnFalse() {
        SimpleBoard board = new SimpleBoard();
        assertFalse(board.isFilled());
    }

    @Test
    public void whenBoardNoMoveIsFilledShouldReturnTrue() {
        SimpleBoard board = new SimpleBoard();
        for (int j = 0; j < board.height(); j++) {
            for (int i = 0; i < board.width(); i++) {
                board.changeSymbolAt(Mark.O, i, j);
            }
        }
        assertTrue(board.isFilled());
    }
}
