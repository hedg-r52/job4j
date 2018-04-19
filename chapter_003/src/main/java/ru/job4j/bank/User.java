package ru.job4j.bank;

/**
 * Пользователь
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable {
    private final String name;
    private final String passport;

    public User(String name, String passport) {
        this.name = name;
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public String getPassport() {
        return passport;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + passport.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (!name.equals(user.name)) {
            return false;
        }
        return passport.equals(user.passport);
    }

    public boolean isNull() {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return this.passport.compareTo(((User) o).passport);
    }
}
