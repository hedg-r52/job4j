package demo;

/**
 * Memory usage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MemoryUsage {

    public static void main(String[] args) {
        System.out.println("User with fields:");
        usageUser();
        System.out.println("User with no fields:");
        usageUserNoFields();
    }

    private static void usageUser() {
        info("start");
        User user = new User("test");
        info("after creating user");
        user = null;
        System.gc();
        info("after null and System.gc() ");
    }

    private static void usageUserNoFields() {
        info("start");
        UserNoFields user = new UserNoFields();
        info("after creating user");
        user = null;
        System.gc();
        info("after null and System.gc() ");
    }

    public static void info(String text) {
        Runtime runtime = Runtime.getRuntime();
        System.out.printf("##### %s  #####%n", text);
        System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()));
        System.out.println("Free Memory:" + runtime.freeMemory());
        System.out.println("Total Memory:" + runtime.totalMemory());
        System.out.println("Max Memory:" + runtime.maxMemory());
        System.out.println("#################################");
    }

    public static void info() {
        info("Heap utilization statistics");
    }
}
