package tictactoe.logic;

import tictactoe.Mark;
import tictactoe.players.*;
import tictactoe.board.*;
import tictactoe.symbols.*;

/**
 * Common logic
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class CommonLogic implements Logic {
    private Board board = new SimpleBoard(new DoubleSymbols());
    private boolean exit = false;
    private Player[] players = new Player[2];
    private int currentPlayer;

    /**
     * Constructor
     */
    public CommonLogic() {
        this.restart();
        players[0] = new HumanPlayer(Mark.X, this);
        players[1] = new ComputerPlayer(Mark.O, this);
    }

    /**
     * Check win player with X-marks
     * @return true if X-marks three-in-line
     */
    @Override
    public boolean isWinX() {
        return isWin(Mark.X);
    }

    /**
     * Check win player with O-marks
     * @return true if O-marks three-in-line
     */
    @Override
    public boolean isWinO() {
        return isWin(Mark.O);
    }

    /**
     * restart logic for new game
     */
    @Override
    public void restart() {
        this.currentPlayer = 0;
        this.board.clear();
    }

    /**
     * Make turn
     * @param mark X-mark or O-mark
     * @param x coordinate by x axis (horizontal)
     * @param y coordinate by y axis (vertical)
     * @return true if turn made or false - if not
     */
    @Override
    public boolean turn(Mark mark, int x, int y) {
        boolean result = false;
        if (this.board.isEmpty(x, y)) {
            result = true;
            this.board.changeSymbolAt(mark, x, y);
        }
        return result;
    }

    /**
     * end game
     */
    @Override
    public void end() {
        this.exit = true;
    }

    /**
     * check is game ended
     * @return true if game check as ended
     */
    @Override
    public boolean isEnded() {
        return this.exit;
    }

    /**
     * height
     * @return integer value of height
     */
    @Override
    public int height() {
        return this.board.height();
    }

    /**
     * Check for turn
     * @return true - if no empty cells or false - if empty cells exist.
     */
    @Override
    public boolean noMove() {
        return false;
    }

    /**
     * draw board
     */
    @Override
    public void draw() {
        this.board.draw();
    }

    /**
     * Next turn
     */
    @Override
    public void nextTurn() {
        players[this.currentPlayer].turn();
        this.currentPlayer = (this.currentPlayer + 1) % players.length;
    }

    /**
     * width
     * @return integer value of width
     */
    @Override
    public int width() {
        return this.board.width();
    }

    /**
     * Check wining by mark
     * @param mark checked mark
     * @return true if present three-in-line marks, false - if not three-in-line marks.
     */
    private boolean isWin(Mark mark) {
        boolean result = false;
        for (int i = 0; i < this.board.width(); i++) {
            result = result
                    | (this.board.symbolAt(i, 0) == mark
                    && this.board.symbolAt(i, 1) == mark
                    && this.board.symbolAt(i, 2) == mark)
                    | (this.board.symbolAt(0, i) == mark
                    && this.board.symbolAt(1, i) == mark
                    && this.board.symbolAt(2, i) == mark);
        }
        result = (
                result
                        | (this.board.symbolAt(0, 0) == mark
                        && this.board.symbolAt(1, 1) == mark
                        && this.board.symbolAt(2, 2) == mark)
                        | (this.board.symbolAt(0, 2) == mark
                        && this.board.symbolAt(1, 1) == mark
                        && this.board.symbolAt(2, 0) == mark)
        );
        return result;
    }
}
