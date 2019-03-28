package tictactoe;

import tictactoe.logic.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Game
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class Game {
    private Map<Status, Consumer<Status>> dispatch = new HashMap<>();
    private Logic logic = new CommonLogic();

    /**
     * Constructor
     */
    public Game() {
        this.init();
    }

    /**
     * Init status values
     */
    private void init() {
        dispatch.put(Status.NOT_WIN, this::handleNotWin);
        dispatch.put(Status.WIN_X, this::handleWinX);
        dispatch.put(Status.WIN_O, this::handleWinO);
        dispatch.put(Status.NO_TURN, this::handleNoTurn);
    }

    /**
     * handle for NoTurn status
     * @param status input status
     */
    private void handleNoTurn(Status status) {
        System.out.println("No turn!");
        requestForNewGame();
    }

    /**
     * handle for NotWin status
     * @param status input status
     */
    private void handleNotWin(Status status) {
        System.out.println("Next turn:");
        this.logic.nextTurn();
    }

    /**
     * handle for WinX status
     * @param status input status
     */
    private void handleWinX(Status status) {
        System.out.println("Win X player!");
        requestForNewGame();
    }

    /**
     * handle for WinO status
     * @param status input status
     */
    private void handleWinO(Status status) {
        System.out.println("Win O player!");
        requestForNewGame();
    }

    /**
     * Request for beginning new game
     */
    private void requestForNewGame() {
        System.out.println("Restart game (y - restart, another - exit)?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if ("y".equals(answer.toLowerCase())) {
            logic.restart();
        } else {
            logic.end();
        }
    }

    /**
     * start method - main loop
     */
    public void start() {
        while (!logic.isEnded()) {
            this.logic.draw();
            Status status = requestStatus();
            this.dispatch.get(status).accept(status);
        }
    }

    /**
     * Request status
     * @return current status of game
     */
    private Status requestStatus() {
        Status status = (this.logic.noMove() ? Status.NO_TURN : Status.NOT_WIN);
        status = (logic.isWinO() ? Status.WIN_O : status);
        status = (logic.isWinX() ? Status.WIN_X : status);
        return status;
    }

    /**
     * Main
     * @param args args
     */
    public static void main(String[] args) {
        new Game().start();
    }
}
