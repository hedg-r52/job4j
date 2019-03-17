package lsp.foods;

import java.util.Date;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public class Pork extends Food {
    public Pork(String name, Date createDate, Date expiredDate, float price, float discount) {
        super(name, createDate, expiredDate, price, discount);
    }
}
