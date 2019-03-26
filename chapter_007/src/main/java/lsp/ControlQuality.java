package lsp;

import lsp.foods.IFood;
import lsp.warehouses.IStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Control quality
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.03.2019
 * @version 0.2
 */
public class ControlQuality {
    private final List<IStorage> storages = new ArrayList<>();

    /**
     * Adding storage to collection
     * @param storage adding storage
     */
    public void addStorage(IStorage storage) {
        storages.add(storage);
    }

    public IStorage checkQuality(IFood food, Date currentDate) {
        IStorage storage = this.relocate(food, currentDate);
        checkDiscount(food, currentDate);
        return storage;
    }

    /**
     * Dynamically food resort
     * @param currentDate date on resort
     */
    public void resort(Date currentDate) {
        for (IStorage storage : this.storages) {
            List<IFood> foods = storage.getAllFood();
            storage.clean();
            for (IFood food : foods) {
                this.relocate(food, currentDate);
            }
        }
    }

    /**
     * Check discount
     *
     * if days left value between upper and lower thresholds then enable sale
     * @param food
     * @param currentDate
     */
    private void checkDiscount(IFood food, Date currentDate) {
        if (food.getDaysOfLifeInPercent(currentDate) >= Thresholds.LOWER_DISCOUNT_THRESHOLD
                && food.getDaysOfLifeInPercent(currentDate) <= Thresholds.UPPER_DISCOUNT_THRESHOLD) {
            food.enableSale();
        }
    }

    /**
     * relocate food to storage
     * @param food food
     * @param currentDate day of relocate
     * @return storage
     */
    private IStorage relocate(IFood food, Date currentDate) {
        IStorage result = null;
        for (IStorage storage : storages) {
            if (storage != null && storage.isSuitable(food, currentDate)) {
                storage.add(food);
                result = storage;
                break;
            }
        }
        return result;
    }
}
