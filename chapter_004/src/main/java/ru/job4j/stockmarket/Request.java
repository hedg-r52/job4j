package ru.job4j.stockmarket;

import java.util.Date;

/**
 * Заявка
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Request {
    private long id;
    private String book;
    private RequestType type;
    private RequestAction action;
    private double price;
    private int volume;

    public Request(String book, RequestType type, RequestAction action, double price, int volume) {
        final Date date = new Date();
        this.id = date.getTime();
        this.book = book;
        this.type = type;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    public long getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public RequestType getType() {
        return type;
    }

    public RequestAction getAction() {
        return action;
    }

    public Double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }


}
