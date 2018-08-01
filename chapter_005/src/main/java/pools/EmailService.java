package pools;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {
    private final List<User> notificationList;
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );
    private final EmailNotification emailNotification = new EmailNotification();

    public EmailService(List<User> notificationList) {
        this.notificationList = notificationList;
    }

    private class Sender implements Runnable {
        private final User user;

        public Sender(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            emailNotification.emailTo(user);
        }
    }

    public void init() {
        for (User user : this.notificationList) {
            this.pool.submit(new Sender(user));
        }
        this.pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
