package lsp.foods;

import java.util.Date;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public interface IFood {
    boolean needColdStorage();
    boolean canReproduct();
    int getDaysOfLifeInPercent(Date currentDate);
    float getPrice();
    void enableSale();
}
