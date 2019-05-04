package demo;

/**
 * User with fields
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class User {
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }
}
