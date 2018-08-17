package nonblockingalgo;

public class OptimisticException extends RuntimeException {
    public OptimisticException() {
        super("The modified model is not equal to the one in the cache.");
    }
}
