package exam;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Switcher {

    private StringBuilder sb;
    public static final int SEQUENCE_LENGTH = 10;
    final Semaphore sem1 = new Semaphore(0);
    final Semaphore sem2 = new Semaphore(1);

    public Switcher() {
        this.sb = new StringBuilder();
    }

    public void convertAndAdd(int number) {
        sb.append(number);
    }

    public String getValue() {
        return sb.toString();
    }

    public static void main(String[] args) {
        Switcher sw = new Switcher();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Adder(sw, sw.sem1, sw.sem2, 1));
        exec.execute(new Adder(sw, sw.sem2, sw.sem1, 2));
        exec.shutdown();
        System.out.println(sw.getValue());

    }

}

class Adder implements Runnable {
    final Switcher switcher;
    final Semaphore semaphoreOwn;
    final Semaphore semaphoreOther;
    final int number;
    private final int repeating = 5;

    public Adder(Switcher switcher, Semaphore semaphoreOwn, Semaphore semaphoreOther, int number) {
        this.switcher = switcher;
        this.semaphoreOwn = semaphoreOwn;
        this.semaphoreOther = semaphoreOther;
        this.number = number;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < repeating) {
            try {
                semaphoreOther.acquire();
                for (int j = 0; j < Switcher.SEQUENCE_LENGTH; j++) {
                    switcher.convertAndAdd(number);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphoreOwn.release();
            i++;
        }
    }
}