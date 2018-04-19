package ru.job4j.bank;

/**
 * "Нулевой" счет
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class NullAccount extends Account {

    public NullAccount(double values, String requisites) {
        super(values, requisites);
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
