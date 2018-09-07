package wrapper;

import org.junit.Test;

import java.util.Iterator;

public class ThreadSafeIterableTest {
    @Test
    public void whenTest() throws InterruptedException {
        Container<Integer> threadSafeContainer = new ArrayContainer<>();
        threadSafeContainer.add(1);
        threadSafeContainer.add(2);
        threadSafeContainer.add(3);
        threadSafeContainer.add(4);
        threadSafeContainer.add(5);

        TestThread<Integer> thread1 = new TestThread<>(threadSafeContainer);
        TestThread<Integer> thread2 = new TestThread<>(threadSafeContainer);
        TestThread<Integer> thread3 = new TestThread<>(threadSafeContainer);
        thread1.start();
        thread2.start();
        thread3.start();
    }

    class TestThread<E> extends Thread {
        Container<E> container;

        public TestThread(Container<E> container) {
            this.container = container;
        }

        @Override
        public void run() {
            Iterator<E> it = container.iterator();
            while (it.hasNext()) {
                System.out.format("%s : %s\n", Thread.currentThread().getName(), it.next());
            }
        }
    }
}


