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

    public void addStorage(IStorage storage) {
        storages.add(storage);
    }

    public IStorage checkQuality(IFood food, Date currentDate) {
        IStorage storage = this.relocate(food, currentDate);
        checkDiscount(food, currentDate);
        return storage;
    }

    public void resort(Date currentDate) {
        for (IStorage storage : this.storages) {
            List<IFood> foods = storage.getAllFood();
            storage.clean();
            for (IFood food : foods) {
                this.relocate(food, currentDate);
            }
        }
    }

    private void checkDiscount(IFood food, Date currentDate) {
        if (food.getDaysOfLifeInPercent(currentDate) >= Thresholds.LOWER_DISCOUNT_THRESHOLD
                && food.getDaysOfLifeInPercent(currentDate) <= Thresholds.UPPER_DISCOUNT_THRESHOLD) {
            food.enableSale();
        }
    }

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
