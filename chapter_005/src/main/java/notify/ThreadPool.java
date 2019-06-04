package notify;

import net.jcip.annotations.ThreadSafe;

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
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();
    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
            new MyThread().start();
        }
    }

    public void work(Runnable job) {
        synchronized (tasks) {
            this.tasks.offer(job);
            this.tasks.notifyAll();
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

    class MyThread extends Thread {
        @Override
        public void run() {
            Runnable r;
            while (!this.isInterrupted()) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    r = (Runnable) tasks.poll();
                }
                try {
                    r.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
