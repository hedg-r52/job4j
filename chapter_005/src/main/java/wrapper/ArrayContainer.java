package wrapper;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Контейнер на массиве
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ArrayContainer<E> extends Container<E> {
    private final Object lock = new Object();
    @GuardedBy("lock")
    protected Object[] container;
    private final static int DEFAULT_CONTAINER_SIZE = 10;
    @GuardedBy("lock")
    private int position;

    public ArrayContainer() {
        this(DEFAULT_CONTAINER_SIZE);
    }

    public ArrayContainer(int size) {
        this.container = new Object[size];
        position = 0;
    }

    public void add(E value) {
        synchronized (lock) {
            if (position >= this.container.length) {
                growContainerSize();
            }
            this.container[position++] = value;
        }
    }

    public E get(int index) {
        synchronized (lock) {
            return (E) this.container[index];
        }
    }

    public boolean contains(E value) {
        boolean result = false;
        synchronized (lock) {
            for (int i = 0; i < position; i++) {
                if (value.equals(this.container[i])) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                synchronized (lock) {
                    return (index < position);
                }
            }

            @Override
            public E next() {
                synchronized (lock) {
                    return (E) container[index++];
                }
            }
        };
    }

    @Override
    protected  Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    private void growContainerSize() {
        synchronized (lock) {
            this.container = Arrays.copyOf(this.container, this.container.length * 2);
        }
    }
}
