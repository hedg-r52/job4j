package functional;

public interface TripleConEx<F, S, T> {
    void accept(F first, S second, T third) throws Exception;
}
