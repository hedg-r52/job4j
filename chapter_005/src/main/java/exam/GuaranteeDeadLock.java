package exam;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuaranteeDeadLock {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(10);
        for (int i = 0; i < 5; i++) {
            exec.execute(new Worker(cdl, "worker" + i));
        }
        cdl.await();
    }
}

class Worker implements Runnable {

    final CountDownLatch cdl;
    final String name;

    public Worker(CountDownLatch cdl, String name) {
        this.cdl = cdl;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Worker#" + name + " do work.");
        cdl.countDown();
    }
}