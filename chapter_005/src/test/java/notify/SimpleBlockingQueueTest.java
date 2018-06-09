package notify;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    SimpleBlockingQueue<Integer> queue;
    private Integer result = 0;

    @Before
    public void setUp() {
        queue = new SimpleBlockingQueue<>(2);
    }

    @Test
    public void whenProducer() throws InterruptedException {
        Thread producer = new Thread() {
            @Override
            public void run() {
                queue.offer(1);
            }
        };
        Thread consumer = new Thread() {
            @Override
            public void run() {
                result = queue.poll();
            }
        };

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(result, is(1));
        assertThat(queue.size(), is(0));
    }

}