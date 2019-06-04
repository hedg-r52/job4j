package wrapper;

abstract class Container<E> implements Iterable<E>, Cloneable {
    abstract void add(E value);
    abstract E get(int index);
    abstract boolean contains(E value);

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
