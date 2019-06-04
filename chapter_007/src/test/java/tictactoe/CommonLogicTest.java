package tictactoe;

import org.junit.Test;
import tictactoe.logic.CommonLogic;
import tictactoe.logic.Logic;

import static org.hamcrest.core.Is.is;
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
        Logic logic = new CommonLogic();
        logic.turn(Mark.X, 0, 0);
        logic.turn(Mark.X, 1, 1);
        logic.turn(Mark.X, 2, 2);
        assertTrue(logic.isWinX());
    }

    @Test
    public void isWinO() {
        Logic logic = new CommonLogic();
        logic.turn(Mark.O, 0, 2);
        logic.turn(Mark.O, 1, 1);
        logic.turn(Mark.O, 2, 0);
        assertTrue(logic.isWinO());
    }

    @Test
    public void isNobodyWin() {
        Logic logic = new CommonLogic();
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
        Logic logic = new CommonLogic();
        logic.turn(Mark.X, 0, 0);
        logic.turn(Mark.X, 1, 1);
        logic.turn(Mark.X, 2, 2);
        assertTrue(logic.isWinX());
        logic.restart();
        assertFalse(logic.isWinX());
        assertFalse(logic.isWinO());
    }

    @Test
    public void whenCreateCommonLogicShouldSizeEqualsThreeByThree() {
        Logic logic = new CommonLogic();
        assertThat(logic.height(), is(3));
        assertThat(logic.width(), is(3));
    }

    @Test
    public void whenCallEndShouldIsEndedEqualsTrue() {
        Logic logic = new CommonLogic();
        assertFalse(logic.isEnded());
        logic.end();
        assertTrue(logic.isEnded());
    }

    @Test
    public void whenHasFieldsForMoveNoMoveShouldReturnFalse() {
        Logic logic = new CommonLogic();
        assertFalse(logic.noMove());
    }

    @Test
    public void whenFillAllFieldNoMoveShouldReturnTrue() {
        Logic logic = new CommonLogic();
        for (int j = 0; j < logic.height(); j++) {
            for (int i = 0; i < logic.width(); i++) {
                logic.turn(Mark.O, i, j);
            }
        }
        assertTrue(logic.noMove());
    }
}
