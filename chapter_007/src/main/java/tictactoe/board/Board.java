package tictactoe.board;

import tictactoe.Mark;

/**
 * Board
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public interface Board {
    /**
     * get board width
     * @return integer value of width
     */
    int width();

    /**
     * get board height
     * @return integer value of height
     */
    int height();

    /**
     * draw board
     */
    void draw();

    /**
     * clear cells
     */
    void clear();

    /**
     * Change symbol at cell
     * @param mark inserting symbol
     * @param x coordinate by x axis
     * @param y coordinate by y axis
     */
    void changeSymbolAt(Mark mark, int x, int y);

    /**
     * Get symbol at cell
     * @param x coordinate by x axis
     * @param y coordinate by y axis
     * @return symbol ar coordinates
     */
    Mark symbolAt(int x, int y);

    /**
     * Check cell at cell
     * @param x coordinate by x axis
     * @param y coordinate by x axis
     * @return true - if no mark at cell, false - if mark present
     */
    boolean isEmpty(int x, int y);

    /**
     * String representation of board
     * @return string representation
     */
    String getGridWithValues();
}
