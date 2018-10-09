package bomberman.version2;

import javafx.scene.shape.Rectangle;

import java.util.concurrent.locks.ReentrantLock;

class Cell extends Rectangle {
    private final int x;
    private final int y;
    protected final Board board;
    protected ReentrantLock lock;

    public Cell(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        lock = new ReentrantLock();
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public boolean isLocked() {
        return lock.isLocked();
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }
}
