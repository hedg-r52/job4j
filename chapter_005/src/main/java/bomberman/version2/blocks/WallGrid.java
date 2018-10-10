package bomberman.version2.blocks;

import java.util.concurrent.CountDownLatch;

import bomberman.version2.Logic;
import bomberman.version2.blocks.Wall;

public class WallGrid implements Runnable {
    private final CountDownLatch latch;
    private final Logic logic;
    private final int width;
    private final int height;

    public WallGrid(CountDownLatch latch, Logic logic, int width, int height) {
        this.latch = latch;
        this.logic = logic;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        int cycleLimX = (height % 4 != 0 ? height / 2 + 1 : height / 2);
        int cycleLimY = (width % 4 != 0 ? width / 2 + 1 : width / 2);
        for (int y = 2; y < cycleLimY; y += 2) {
            for (int x = 2; x < cycleLimX; x += 2) {
                logic.init(x, y, new Wall());
                logic.init(x, width - y - 1, new Wall());
                logic.init(height - 1 - x, y, new Wall());
                logic.init(height - 1 - x, width - y - 1, new Wall());
            }
        }
        latch.countDown();
    }
}