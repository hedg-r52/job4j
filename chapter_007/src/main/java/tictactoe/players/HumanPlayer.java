package tictactoe.players;

import tictactoe.Mark;
import tictactoe.logic.Logic;
import java.util.Scanner;

/**
 * Human player
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class HumanPlayer extends CommonPlayer {
    /**
     * Constructor
     * @param mark player mark
     * @param logic Logic
     */
    public HumanPlayer(Mark mark, Logic logic) {
        super(mark, logic);
    }

    /**
     * Player turn
     */
    @Override
    public void turn() {
        boolean makeTurn = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Input coordinates your turn (separated by spaces):");
            String answer = scanner.nextLine();
            if (answer.matches("\\d\\s\\d")) {
                String[] parts = answer.split("\\s", 2);
                makeTurn = this.logic.turn(mark, Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
            }
        } while (!makeTurn);
    }
}
