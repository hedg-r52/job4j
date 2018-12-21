package functional;

public interface BiConEx<L, R> {
    void accept(L left, R right) throws Exception;
}
