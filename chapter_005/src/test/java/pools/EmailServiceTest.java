package pools;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class EmailServiceTest {
    private List<User> users = new LinkedList<>(
            Arrays.asList(
                    new User("Anhel", "Anhel@mail.no"),
                    new User("Bjorn", "Bjorn@mail.no"),
                    new User("Clark", "Clark@mail.no"),
                    new User("Donald", "Donald@mail.no"),
                    new User("Eugene", "Eugene@mail.no"),
                    new User("Frank", "Frank@mail.no"),
                    new User("George", "George@mail.no"),
                    new User("Harry", "Harry@mail.no"),
                    new User("Ivarr", "Ivarr@mail.no"),
                    new User("John", "John@mail.no"),
                    new User("Kevin", "Kevin@mail.no"),
                    new User("Larry", "Larry@mail.no"),
                    new User("Michael", "Michael@mail.no"),
                    new User("Nick", "Nick@mail.no"),
                    new User("Ocean", "Ocean@mail.no"),
                    new User("Philipp", "Philipp@mail.no")
            )
    );
    private EmailService service;

    @Before
    public void setUp() {
        service = new EmailService();
    }

    @Test
    public void whenInit() {
        for (User user: users) {
            service.send(user);
        }
        service.stop();
    }
}