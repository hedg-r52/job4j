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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void whenDrawGridWithValuesShouldDrawGridAndXAtOneOneAndOAtTwoTwoCells() {
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
}
