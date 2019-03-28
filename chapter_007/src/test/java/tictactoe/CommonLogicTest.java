package tictactoe;

import org.junit.Test;
import tictactoe.logic.CommonLogic;
import static org.junit.Assert.*;

/**
 * Test for CommonLogic
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class CommonLogicTest {
    @Test
    public void isWinX() {
        CommonLogic logic = new CommonLogic();
        logic.turn(Mark.X, 0, 0);
        logic.turn(Mark.X, 1, 1);
        logic.turn(Mark.X, 2, 2);
        assertTrue(logic.isWinX());
    }

    @Test
    public void isWinO() {
        CommonLogic logic = new CommonLogic();
        logic.turn(Mark.O, 0, 2);
        logic.turn(Mark.O, 1, 1);
        logic.turn(Mark.O, 2, 0);
        assertTrue(logic.isWinO());
    }

    @Test
    public void isNobodyWin() {
        CommonLogic logic = new CommonLogic();
        logic.turn(Mark.O, 0, 0);
        logic.turn(Mark.X, 0, 1);
        logic.turn(Mark.O, 0, 2);
        logic.turn(Mark.X, 1, 0);
        logic.turn(Mark.X, 1, 2);
        logic.turn(Mark.O, 2, 0);
        logic.turn(Mark.X, 2, 1);
        logic.turn(Mark.O, 2, 2);
        assertFalse(logic.isWinX());
        assertFalse(logic.isWinO());
    }

    @Test
    public void whenWinXAndRestartShouldNobodyWin() {
        CommonLogic logic = new CommonLogic();
        logic.turn(Mark.X, 0, 0);
        logic.turn(Mark.X, 1, 1);
        logic.turn(Mark.X, 2, 2);
        assertTrue(logic.isWinX());
        logic.restart();
        assertFalse(logic.isWinX());
        assertFalse(logic.isWinO());
    }
}
