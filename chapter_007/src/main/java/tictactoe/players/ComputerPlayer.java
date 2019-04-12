package tictactoe.players;

import tictactoe.Mark;
import tictactoe.logic.Logic;
import java.util.Random;

/**
 * CPU player
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class ComputerPlayer extends CommonPlayer {
    private Random random = new Random();

    /**
     * Constructor
     * @param mark player mark
     * @param logic Logic
     */
    public ComputerPlayer(Mark mark, Logic logic) {
        super(mark, logic);
    }

    /**
     * CPU turn
     */
    @Override
    public void turn() {
        boolean makeTurn = false;
        int x, y;
        System.out.print("Computer turn: ");
        do {
            int turn = random.nextInt(this.logic.height() * this.logic.width());
            x = turn % this.logic.width();
            y = turn / this.logic.height();
            makeTurn = this.logic.turn(mark, x, y);
        } while (!makeTurn);
        System.out.printf("%s %s%s", x, y, System.getProperty("line.separator"));
    }
}
