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
    private String password;
    private String role;

    public User(String name, String login, String email, String password, String role) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = LocalDateTime.now();
    }

    public User(int id, String name, String login, String email, String password, String role) {
        this(name, login, email, password, role);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }



    public LocalDateTime createDate() {
        return this.createDate;
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

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
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
