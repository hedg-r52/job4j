package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Simple Blocking Queue
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final static int QUEUE_SIZE = 10;
    private final Object lock = new Object();
    private int size;

    @GuardedBy("this")
    private Queue<T> queue;

    /**
     * Constructor with default size of queue
     */
    public SimpleBlockingQueue() {
        this(QUEUE_SIZE);
    }

    /**
     * Constructor
     * @param size size of queue
     */
    public SimpleBlockingQueue(int size) {
        this.size = size;
        this.queue = new LinkedList<>();
    }

    /**
     * Method return size of queue
     * @return size
     */
    @GuardedBy("lock")
    public int size() {
        synchronized (lock) {
            return this.queue.size();
        }
    }

    /**
     * Push element to the queue
     * @param value value for insert into queue
     */
    @GuardedBy("lock")
    public void offer(T value) {
        synchronized (lock) {
            while (this.queue.size() == this.size) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.queue.offer(value);
            lock.notify();
        }
    }

    /**
     * Pop element from the queue
     * @return value at top of queue
     */
    @GuardedBy("lock")
    public T poll() {
        synchronized (lock) {
            while (this.queue.size() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T result = this.queue.poll();
            lock.notify();
            return result;
        }
    }
}