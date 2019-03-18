package lsp.warehouses;

import lsp.foods.IFood;
import java.util.Date;

/**
 * Recycling storage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class RecyclingStorage extends Trash {
    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return food.canReproduct() && super.isSuitable(food, currentDate);
    }
}
