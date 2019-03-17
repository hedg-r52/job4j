package lsp.warehouses;

import lsp.foods.Food;
import java.util.Date;

/**
 * Warehouse
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public class Warehouse extends AbstractStorage {

    public static final int UPPER_BOUND_THRESHOLD = 25;
    public static final int LOWER_BOUND_THRESHOLD = 0;

    @Override
    public boolean isSuitable(Food food, Date currentDate) {
        return food.getDaysOfLifeInPercent(currentDate) >= LOWER_BOUND_THRESHOLD
                && food.getDaysOfLifeInPercent(currentDate) < UPPER_BOUND_THRESHOLD;
    }
}
