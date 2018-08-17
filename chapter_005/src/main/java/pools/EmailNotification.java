package pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body    = String.format("Add a new event to %s", user.getUsername());
        send(subject, body, user.getEmail());
    }

    public synchronized void send(String subject, String body, String email) {
        System.out.println(email);
        System.out.println(subject);
        System.out.println(body);
        System.out.println();
    }
}
