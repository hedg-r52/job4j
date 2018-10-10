package bomberman.version2;

import java.util.concurrent.*;

import bomberman.version2.blocks.WallBorder;
import bomberman.version2.blocks.WallGrid;
import bomberman.version2.exceptions.OutOfBoundsException;
import bomberman.version2.livestock.Hero;
import bomberman.version2.livestock.Monster;

public class Bomberman {

    public static void main(String[] args) throws OutOfBoundsException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Logic logic = new Logic(23, 15, exec);
        CountDownLatch latch = new CountDownLatch(2);
        exec.execute(new WallBorder(latch, logic, logic.getWidth(), logic.getHeight()));
        exec.execute(new WallGrid(latch, logic, logic.getWidth(), logic.getHeight()));
        latch.await();
        exec.execute(logic);
        exec.execute(new Monster(1, 1, logic));
        exec.execute(new Monster(2, 3, logic));
        exec.execute(new Monster(3, 2, logic));
        exec.execute(new Hero(1, 3, logic));
        System.out.println("End");
    }
}
