package bomberman.version2;

import bomberman.OutOfBoundsException;
import java.util.concurrent.TimeUnit;

public class Board {
    private Cell[][] board;
    private final static int MILLIS_FOR_TRYLOCK = 500;
    private int width;
    private int height;

    public Board(int width, int height) throws OutOfBoundsException {
        if (width > 0 && height > 0) {
            this.width = width;
            this.height = height;
            this.board = new Cell[this.width][this.height];

            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    this.board[i][j] = new Cell(i, j, this);
                }
            }
        } else {
            throw new OutOfBoundsException();
        }
    }

    public boolean move(Cell source, Cell dest) throws InterruptedException {
        if (dest.getCoordX() < 0
                || dest.getCoordX() >= width
                || dest.getCoordY() < 0
                || dest.getCoordY() >= height) {
            return false;
        }
        boolean result = board[dest.getCoordX()][dest.getCoordY()].lock.tryLock(MILLIS_FOR_TRYLOCK, TimeUnit.MILLISECONDS);
        if (result) {
            board[source.getCoordX()][source.getCoordY()].lock.unlock();
            result = true;
        }
        return result;
    }

    public boolean init(Cell dest) throws InterruptedException {
        boolean result = board[dest.getCoordX()][dest.getCoordY()].lock.tryLock(MILLIS_FOR_TRYLOCK, TimeUnit.MILLISECONDS);
        return result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
