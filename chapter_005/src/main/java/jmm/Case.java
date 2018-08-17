package jmm;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Case {
    static Integer a = 0;

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("Before:%s:%s", Thread.currentThread().getName(), a));
                a++;
                System.out.println(String.format("After:%s:%s", Thread.currentThread().getName(), a));
            }
        };
        new Thread(r, "Thread1").start();
        new Thread(r, "Thread2").start();
        new Thread(r, "Thread3").start();
    }

}
