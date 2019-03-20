package lsp.warehouses;

import lsp.Thresholds;
import lsp.foods.IFood;
import lsp.warehouses.decorator.StorageDecorator;

import java.util.Date;

/**
 * Recycling storage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class RecyclingStorage extends StorageDecorator {
    public RecyclingStorage(IStorage storage) {
        super(storage);
    }

    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return food.canReproduct() && food.getDaysOfLifeInPercent(currentDate) > Thresholds.TRASH_START;
    }
}
