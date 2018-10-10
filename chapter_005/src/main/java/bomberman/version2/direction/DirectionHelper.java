package bomberman.version2.direction;

import static bomberman.version2.direction.Direction.*;

public class DirectionHelper {
    public static Direction clockwiseNext(Direction direction) {
        Direction result;
        switch (direction) {
            case WEST:
                result = SOUTH;
                break;
            case SOUTH:
                result = EAST;
                break;
            case EAST:
                result = NORTH;
                break;
            default:
                result = WEST;
                break;
        }
        return result;
    }
}
