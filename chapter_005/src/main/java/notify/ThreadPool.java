package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Pool of threads, which run tasks
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();
    private static final int SIZE = Runtime.getRuntime().availableProcessors();
    private Object lock = new Object();

    @GuardedBy("lock")
    public synchronized void run() {
        for (int i = 0; i < SIZE; i++) {
            Thread thread = new Thread(() -> {
                synchronized (lock) {
                    while (!Thread.currentThread().isInterrupted()) {
                        while (tasks.size() == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        tasks.poll().run();
                    }
                }
            });
            thread.start();
        }
    }

    @GuardedBy("lock")
    public void work(Runnable job) {
        synchronized (lock) {
            this.tasks.offer(job);
            lock.notifyAll();
        }
    }

    public void shutdown() {
        Runnable stop = new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().interrupt();
            }
        };
        for (int i = 0; i < SIZE; i++) {
            this.tasks.offer(stop);
        }
    }
}
