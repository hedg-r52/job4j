package ru.job4j.map;

import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Тестирование класса User
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserTest {

    @Test
    public void overrideEquals() {
        User first = new User("User", 0, new GregorianCalendar(1980, 1, 1));
        User second = new User("User", 0, new GregorianCalendar(1980, 1, 1));
        Map<User, Object> map = new HashMap<>();
        map.put(first, "first");
        map.put(second, "second");
        for (Map.Entry<User, Object> entry : map.entrySet()) {
            System.out.println(entry);
        }
    }
}