package bomberman.version2;

import bomberman.directions.Direction;

public interface Movable {
    Cell move(int x, int y, Direction direction);
}
