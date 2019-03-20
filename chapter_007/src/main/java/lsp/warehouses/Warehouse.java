package lsp.warehouses;

import lsp.Thresholds;
import lsp.foods.IFood;
import lsp.warehouses.decorator.StorageDecorator;
import java.util.Date;

/**
 * Warehouse
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.03.2019
 * @version 0.2
 */
public class Warehouse extends StorageDecorator {
    public Warehouse(IStorage storage) {
        super(storage);
    }

    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return food.getDaysOfLifeInPercent(currentDate) >= Thresholds.WAREHOUSE_START
                && food.getDaysOfLifeInPercent(currentDate) < Thresholds.SHOP_START;
    }
}
