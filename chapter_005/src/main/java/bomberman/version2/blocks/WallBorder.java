package bomberman.version2.blocks;

import java.util.concurrent.CountDownLatch;

import bomberman.version2.Logic;
import bomberman.version2.blocks.Wall;


public class WallBorder implements Runnable {
    private final CountDownLatch latch;
    private Logic logic;
    private final int width;
    private final int height;

    public WallBorder(CountDownLatch latch, Logic logic, int width, int height) {
        this.latch = latch;
        this.logic = logic;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        for (int y = 0; y < width; y++) {
            logic.init(0, y, new Wall());
            logic.init(height - 1, y, new Wall());
        }
        for (int x = 1; x < this.height - 1; x++) {
            logic.init(x, 0, new Wall());
            logic.init(x, width - 1, new Wall());
        }
        latch.countDown();
    }
}