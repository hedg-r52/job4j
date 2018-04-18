package ru.job4j.sort;

/**
 * Пользователь
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable<User> {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int compareTo(User o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return String.format("User{name='%s', age=%s}", name, age);
    }
}
