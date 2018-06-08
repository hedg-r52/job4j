package sync;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    UserStorage storage;
    User user1;
    User user2;

    @Before
    public void setUp() {
        storage = new UserStorage();
        user1 = new User(1, 100);
        user2 = new User(2, 200);
        storage.add(user1);
        storage.add(user2);
    }


    @Test
    public void whenTransfer() {
        storage.transfer(1, 2, 50);
        assertThat(storage.getUserAmount(1), is(50));
        assertThat(storage.getUserAmount(2), is(250));
    }

    @Test
    public void whenDelete() {
        int id = user1.getId();
        assertThat(storage.isUserExist(id), is(true));
        storage.delete(user1);
        assertThat(storage.isUserExist(id), is(false));
    }

    @Test
    public void whenUpdate() {
        final int expected = 300;
        User newUser = new User(1, expected);
        storage.update(newUser);
        assertThat(storage.getUserAmount(1), is(expected));
    }

}