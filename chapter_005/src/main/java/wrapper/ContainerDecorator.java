package wrapper;

import java.util.Iterator;

public class ContainerDecorator<E> extends Container<E> {
    protected Container<E> container;

    public ContainerDecorator(Container<E> container) {
        this.container = container;
    }

    @Override
    public Iterator<E> iterator() {
        return this.container.iterator();
    }

    @Override
    public void add(E value) {
        this.container.add(value);
    }

    @Override
    public E get(int index) {
        return this.container.get(index);
    }

    @Override
    public boolean contains(E value) {
        return this.container.contains(value);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
