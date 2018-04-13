package ru.job4j.list;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO description
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    private static int counter = 0;

    public HashMap<Integer, User> process(List<User> list) {
        Map<Integer, User> result = new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return (HashMap) result;
    }
}
