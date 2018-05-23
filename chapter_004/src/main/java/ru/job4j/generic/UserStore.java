package ru.job4j.generic;

/**
 * Хранилище пользователей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserStore extends AbstractStore<User> {

    public UserStore(int count) {
        super(count);
    }
}
