package bomberman.version2.livestock;

import bomberman.version2.direction.Direction;
import bomberman.version2.direction.DirectionHelper;
import bomberman.version2.Logic;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Monster extends ReentrantLock implements Runnable {
    private int x;
    private int y;
    private Direction direction;
    private Logic logic;
    private Random rand = new Random(42);

    public Monster(int x, int y, Logic logic) {
        this.x = x;
        this.y = y;
        this.direction = Direction.values()[rand.nextInt(3)];
        this.logic = logic;
        logic.init(x, y, this);
    }

    public void move() {
        int newX = x + direction.getDeltaX();
        int newY = y + direction.getDeltaY();

        // проверка хода по новым координатам, если нет возможности сходить то сменить направление
        if (logic.isEmptyBlock(newX, newY)) {
            logic.move(x, y, newX, newY);
            x = newX;
            y = newY;
            // element of chaos, 10%
            int ratio = 100;
            if (rand.nextInt(ratio) > ratio * 0.90) {
                direction = Direction.values()[rand.nextInt(3)];
            }
        } else if (logic.isHero(newX, newY)) {
            logic.setCaught(true);
        } else {
            direction = DirectionHelper.clockwiseNext(direction);
        }
    }

    public String toString() {
        return String.format("Monster: %s,  coord[%s, %s]", Thread.currentThread().getName(), x, y);
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && !logic.isCaught()) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            move();
        }
    }
}
