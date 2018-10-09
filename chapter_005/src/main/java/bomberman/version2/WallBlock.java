package bomberman.version2;

public class WallBlock extends Cell {

    public WallBlock(int x, int y, Board board) {
        super(x, y, board);
        lock.lock();
    }

}
