package lsp.warehouses;

import lsp.foods.IFood;
import java.util.Date;

/**
 * ColdWarehouse
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class ColdWarehouse extends Warehouse {
    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return food.needColdStorage() && food.getDaysOfLifeInPercent(currentDate) >= LOWER_BOUND_THRESHOLD
                && food.getDaysOfLifeInPercent(currentDate) < UPPER_BOUND_THRESHOLD;
    }
}
