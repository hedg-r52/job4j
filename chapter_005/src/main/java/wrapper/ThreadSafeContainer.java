package wrapper;

import net.jcip.annotations.GuardedBy;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;

public class ThreadSafeContainer<E> extends ContainerDecorator<E> {

    public ThreadSafeContainer(Container<E> container) {
        super(container);
    }

    @Override
    public Iterator<E> iterator() {
        return copy(this.container).iterator();
    }

    @GuardedBy("this")
    private Container<E> copy(Container<E> component) {
        Container<E> result = null;
        try {
            result = (Container<E>) component.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }

}
