package ru.job4j.generic;

/**
 * Хранилище ролей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RoleStore extends AbstractStore<Role> {

    public RoleStore(int count) {
        this.store = new Role[count];
        this.position = 0;
    }
}
