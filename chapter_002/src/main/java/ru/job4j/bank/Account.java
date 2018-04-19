package ru.job4j.bank;

/**
 * Счет
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Account {
    private double values;
    private String requisites;

    public Account(double values, String requisites) {
        this.values = values;
        this.requisites = requisites;
    }

    public double getValues() {
        return this.values;
    }

    public String getRequisites() {
        return this.requisites;
    }

    public void setValues(double values) {
        this.values = values;
    }

    public boolean isNull() {
        return false;
    }
}
