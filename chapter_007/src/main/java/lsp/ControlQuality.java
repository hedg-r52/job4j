package lsp;

import lsp.foods.Food;
import lsp.warehouses.AbstractStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public class ControlQuality {
    private final List<AbstractStorage> storages = new ArrayList<>();
    private static final int LOWER_DISCOUNT_THRESHOLD = 75;
    private static final int UPPER_DISCOUNT_THRESHOLD = 100;

    public void addStorage(AbstractStorage storage) {
        storages.add(storage);
    }

    public AbstractStorage checkQuality(Food food, Date currentDate) {
        AbstractStorage storage = this.relocate(food, currentDate);
        checkDiscount(food, currentDate);
        return storage;
    }

    private void checkDiscount(Food food, Date currentDate) {
        if (food.getDaysOfLifeInPercent(currentDate) >= LOWER_DISCOUNT_THRESHOLD
                && food.getDaysOfLifeInPercent(currentDate) <= UPPER_DISCOUNT_THRESHOLD) {
            food.enableSale();
        }
    }

    private AbstractStorage relocate(Food food, Date currentDate) {
        AbstractStorage result = null;
        for (AbstractStorage storage : storages) {
            if (storage != null && storage.isSuitable(food, currentDate)) {
                storage.add(food);
                result = storage;
                break;
            }
        }
        return result;
    }
}
