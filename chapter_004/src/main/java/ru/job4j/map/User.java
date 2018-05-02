package ru.job4j.map;

import java.util.Calendar;
import java.util.Objects;

/**
 * Пользователь
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    public String getName() {
        return this.name;
    }

    public int getChildren() {
        return this.children;
    }

    public Calendar getBirthday() {
        return this.birthday;
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + this.name.hashCode();
        result = 31 * result + this.birthday.hashCode();
        return result;
    }
}
