package buffer;

import notify.SimpleBlockingQueue;

public class ParallelSearch {
    public static void main(String[] args) {
        final int poisonPill = -1;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        int value = queue.poll();
                        if (value != poisonPill) {
                            System.out.println(value);
                        } else {
                            break;
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(poisonPill);
                }
        ).start();
    }
}
