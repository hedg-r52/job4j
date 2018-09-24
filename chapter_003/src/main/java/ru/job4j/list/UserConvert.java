package ru.job4j.list;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Конвертер list -> hashmap
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    public HashMap<Integer, User> process(List<User> list) {
        return list.stream().collect(
                toMap(User::getId, Function.identity(), (e1, e2) -> e2, HashMap::new)
        );
    }
}
