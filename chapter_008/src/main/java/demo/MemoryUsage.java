package demo;

/**
 * Memory usage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MemoryUsage {
    public static class User2 {

    }

    public static void main(String[] args) {
        System.out.println("start");
        info();
        User2 user = new User2();
        info();
        user = null;
        System.gc();
        info();
        System.out.println("finish");
    }

    public static void info() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("##### Heap utilization statistics #####");
        System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("Free Memory:" + runtime.freeMemory());
        System.out.println("Total Memory:" + runtime.totalMemory());
        System.out.println("Max Memory:" + runtime.maxMemory());
    }
}
