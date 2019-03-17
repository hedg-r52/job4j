package lsp.warehouses;

import lsp.foods.Food;
import java.util.Date;

/**
 * Trash
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public class Trash extends AbstractStorage {

    private static final int TRASH_THRESHOLD = 100;

    @Override
    public boolean isSuitable(Food food, Date currentDate) {
        return food.getDaysOfLifeInPercent(currentDate) > TRASH_THRESHOLD;
    }
}
