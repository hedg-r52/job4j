package ru.job4j.bank;

/**
 * "Нулевой" пользователь
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NullUser extends User {

    public NullUser() {
        super("", "");
    }

    public NullUser(String name, String passport) {
        super(name, passport);
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
