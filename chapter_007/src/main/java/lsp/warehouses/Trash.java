package lsp.warehouses;

import lsp.Thresholds;
import lsp.foods.IFood;
import lsp.warehouses.decorator.StorageDecorator;
import java.util.Date;

/**
 * Trash
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.03.2019
 * @version 0.2
 */
public class Trash extends StorageDecorator {
    public Trash(IStorage storage) {
        super(storage);
    }

    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return food.getDaysOfLifeInPercent(currentDate) > Thresholds.TRASH_START;
    }
}
