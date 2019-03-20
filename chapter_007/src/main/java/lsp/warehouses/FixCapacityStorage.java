package lsp.warehouses;

import lsp.foods.IFood;
import lsp.warehouses.decorator.StorageDecorator;

import java.util.Date;

/**
 * Warehouse with fix capacity
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class FixCapacityStorage extends StorageDecorator {
    private final int capacity;
    private int size;

    public FixCapacityStorage(IStorage storage, int capacity) {
        super(storage);
        this.capacity = capacity;
        this.size = this.storage.getSize();
    }


    @Override
    public boolean add(IFood food) {
        boolean result = false;
        if (size < capacity) {
            size++;
            result = super.add(food);
        }
        return result;
    }

    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return (size < capacity) && storage.isSuitable(food, currentDate);
    }
}
