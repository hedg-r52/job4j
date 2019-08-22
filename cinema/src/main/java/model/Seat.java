package model;

import java.util.Objects;

public class Seat implements Comparable<Seat> {
    private int row;
    private int place;
    private int price;
    private boolean sold;

    public Seat(int row, int place, int price, boolean sold) {
        this.row = row;
        this.place = place;
        this.price = price;
        this.sold = sold;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public int compareTo(Seat o) {
        int result = Integer.compare(this.row, o.row);
        return (result != 0 ? result : Integer.compare(this.place, o.place));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return row == seat.row
                && place == seat.place
                && price == seat.price
                && sold == seat.sold;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, place, price, sold);
    }
}
