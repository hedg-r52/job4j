package bomberman.version2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import bomberman.version2.exceptions.OutOfBoundsException;
import bomberman.version2.livestock.Hero;

public class Logic implements Runnable {
    private ReentrantLock[][] board;
    private int width;
    private int height;
    private volatile boolean caught = false;
    private ExecutorService exec;

    public Logic(int width, int height, ExecutorService exec) throws OutOfBoundsException {
        this.exec = exec;
        if (width > 0 && height > 0) {
            this.width = width;
            this.height = height;
            this.board = new ReentrantLock[this.height][this.width];
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    this.board[i][j] = new ReentrantLock();
                }
            }
        } else {
            throw new OutOfBoundsException();
        }
    }

    public boolean isCaught() {
        return caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public synchronized void init(int x, int y, ReentrantLock block) {
        board[x][y] = block;
        board[x][y].lock();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public synchronized void move(int sourceX, int sourceY, int destX, int destY) {
        board[destX][destY] = board[sourceX][sourceY];
        board[sourceX][sourceY] = new ReentrantLock();
    }

    public boolean isHero(int x, int y) {
        return board[x][y].isLocked() && board[x][y] instanceof Hero;
    }

    public boolean isEmptyBlock(int x, int y) {
        return !board[x][y].isLocked();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isCaught()) {
                System.out.println("GOTCHA!");
                exec.shutdown();
                break;
            }
            Thread.yield();
        }
    }
    
}