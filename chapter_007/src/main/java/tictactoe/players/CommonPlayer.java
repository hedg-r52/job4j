package tictactoe.players;

import tictactoe.Mark;
import tictactoe.logic.Logic;

/**
 * Commmon player
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public abstract class CommonPlayer implements Player {
    protected final Mark mark;
    protected final Logic logic;

    /**
     * Constructor
     * @param mark player mark
     * @param logic Logic
     */
    public CommonPlayer(Mark mark, Logic logic) {
        this.mark = mark;
        this.logic = logic;
    }

    /**
     * Player turn
     */
    public abstract void turn();
}
