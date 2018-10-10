package bomberman.version2.livestock;

import bomberman.version2.Logic;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Hero extends ReentrantLock implements Runnable {
    private int x;
    private int y;
    private final static int STEP = 1;
    private Logic logic;
    private Random rand = new Random(42);

    public Hero(int x, int y, Logic logic) {
        this.x = x;
        this.y = y;
        this.logic = logic;
        logic.init(x, y, this);
    }

    public void moveLeft() {
        move(-STEP, 0);
    }

    public void moveRight() {
        move(STEP, 0);
    }

    public void moveDown() {
        move(0, STEP);
    }

    public void moveUp() {
        move(0, -STEP);
    }

    private void move(int deltaX, int deltaY)  {
        if (logic.isEmptyBlock(x + deltaX, y + deltaY)) {
            logic.move(x, y, x + deltaX, y + deltaY);
            x = x + deltaX;
            y = y + deltaY;
        }
    }

    public String toString() {
        return String.format("Hero: %s,  coord[%s, %s]", Thread.currentThread().getName(), x, y);
    }


    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
            moveDown();
            System.out.println(this);
        }
        while (!Thread.interrupted() && !logic.isCaught()) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }
}
