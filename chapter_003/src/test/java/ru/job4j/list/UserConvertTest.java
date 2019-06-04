package ru.job4j.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * UserConvertTest
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    @Test
    public void whenInListUsersThenGetUsersInMap() {
        UserConvert uc = new UserConvert();
        List<User> users = new ArrayList<>();
        User newUser1 = new User(1, "Andrei", "Nizhnij Novgorod");
        User newUser2 = new User(2, "Petr", "Bryansk");
        User newUser3 = new User(3, "Anton", "Moscow");
        users.add(newUser1);
        users.add(newUser2);
        users.add(newUser3);
        Map<Integer, User> result = uc.process(users);
        Map<Integer, User> expected = new HashMap<>();
        expected.put(1, newUser1);
        expected.put(2, newUser2);
        expected.put(3, newUser3);
        assertThat(result, is(expected));
    }

}