package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    private int result = 0;

    private void increment() {
        result++;
    }

    @Test
    public void whenAdd() {
        ThreadPool pool = new ThreadPool();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                increment();
            }
        };
        for (int i = 0; i < 10; i++) {
            pool.work(r);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        assertThat(result, is(10));
    }
}