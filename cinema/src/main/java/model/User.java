package model;

import java.util.Objects;

public class User {
    private String name;
    private String phone;
    private Seat seat;

    public User(String name, String phone, Seat seat) {
        this.name = name;
        this.phone = phone;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
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
        return name.equals(user.name) && phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }
}
