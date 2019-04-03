package tictactoe.logic;

import tictactoe.Mark;

/**
 * Logic
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public interface Logic {
    /**
     * Check win player with X-marks
     * @return true if X-marks three-in-line
     */
    boolean isWinX();

    /**
     * Check win player with O-marks
     * @return true if O-marks three-in-line
     */
    boolean isWinO();

    /**
     * Make turn
     * @param mark X-mark or O-mark
     * @param x coordinate by x axis (horizontal)
     * @param y coordinate by y axis (vertical)
     * @return true if turn made or false - if not
     */
    boolean turn(Mark mark, int x, int y);

    /**
     * restart logic for new game
     */
    void restart();

    /**
     * end game
     */
    void end();

    /**
     * check is game ended
     * @return true if game check as ended
     */
    boolean isEnded();

    /**
     * width
     * @return integer value of width
     */
    int width();

    /**
     * height
     * @return integer value of height
     */
    int height();

    /**
     * Check for turn
     * @return true - if no empty cells or false - if empty cells exist.
     */
    boolean noMove();

    /**
     * draw board
     */
    void draw();

    /**
     * Next turn
     */
    void nextTurn();
}
