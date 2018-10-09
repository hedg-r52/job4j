package bomberman.version2;

public class UnmovableBlockException extends Exception {

    private static final String PREFIX = "Block can't move!";
    public UnmovableBlockException() {
        this(PREFIX);
    }

    public UnmovableBlockException(String message) {
        super(PREFIX + " " + message);
    }
}
