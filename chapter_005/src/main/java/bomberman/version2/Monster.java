package bomberman.version2;

import java.util.concurrent.TimeUnit;

public class Monster extends Cell implements Runnable {

    public Monster(int x, int y, Board board) {
        super(x, y, board);
    }

    @Override
    public void run() {
        try {
            Cell monster = new Monster(1, 1, board);
            board.init(monster);
            while (!Thread.interrupted()) {
                // TODO move

                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println("Monster interrupted");
        }
    }
}
