package bomberman;

import bomberman.directions.Direction;
import org.junit.Test;

/**
 * Created by 22SolovevAE on 20.08.2018.
 */
public class HeroThreadTest {

    @Test
    public void test() throws OutOfBoundsException, InterruptedException {
        Board board = new Board(10, 12);
        HeroThread ht1 = new HeroThread(board, 1, 1, Direction.WEST);
        HeroThread ht2 = new HeroThread(board, 3, 3, Direction.NORTH);
        ht1.start();
        ht2.start();
        ht1.join();
    }
}