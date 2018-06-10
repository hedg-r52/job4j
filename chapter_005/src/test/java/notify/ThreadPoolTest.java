package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@ThreadSafe
public class ThreadPoolTest {
    private final Object lock = new Object();
    @GuardedBy("lock")
    private int result = 0;

    @GuardedBy("lock")
    private void increment() {
        synchronized (lock) {
            result++;
        }
    }

    @Test
    public void whenAdd() {
        ThreadPool pool = new ThreadPool();
        pool.run();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                increment();
            }
        };
        for (int i = 0; i < 10; i++) {
            pool.work(r);
        }
        pool.shutdown();
        assertThat(result, is(10));
    }
}