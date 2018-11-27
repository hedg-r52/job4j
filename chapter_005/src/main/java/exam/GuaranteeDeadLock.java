package exam;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuaranteeDeadLock {
    public static void main(String[] args) throws Exception {
        Object obj1 = new Object();
        Object obj2 = new Object();
        CountDownLatch cdl = new CountDownLatch(2);
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Worker(cdl, obj1, obj2, "worker1"));
        exec.execute(new Worker(cdl, obj2, obj1, "worker2"));
    }
}

class Worker implements Runnable {
    final CountDownLatch cdl;
    Object obj1;
    Object obj2;
    final String name;

    public Worker(CountDownLatch cdl, Object obj1, Object obj2, String name) {
        this.cdl = cdl;
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (obj1) {
            cdl.countDown();
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj2) {
                System.out.println("Worker#" + name + " do work.");
            }
        }
    }
}