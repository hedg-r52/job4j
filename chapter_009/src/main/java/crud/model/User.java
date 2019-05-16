package crud.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * User
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class User {
    private int id;
    private String name;
    private String login;
    private String email;
    private LocalDateTime createDate;

    public User(String name, String login, String email) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createDate = LocalDateTime.now();
    }

    public User(int id, String name, String login, String email) {
        this(name, login, email);
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String login() {
        return this.login;
    }

    public String email() {
        return this.email;
    }

    public LocalDateTime createDate() {
        return this.createDate;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return name.equals(user.name)
                && login.equals(user.login)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("login='" + login + "'")
                .add("email='" + email + "'")
                .add("createDate=" + createDate)
                .toString();
    }
}
