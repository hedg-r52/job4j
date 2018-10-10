package bomberman.version2.direction;

public enum Direction {
    WEST(1, 0),
    SOUTH(0, 1),
    EAST(-1, 0),
    NORTH(0, -1);

    private final int deltaX;
    private final int deltaY;


    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

}
