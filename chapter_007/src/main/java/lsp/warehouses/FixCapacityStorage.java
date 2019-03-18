package lsp.warehouses;

import lsp.foods.IFood;
import java.util.Date;

/**
 * Warehouse with fix capacity
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class FixCapacityStorage extends AbstractStorage {
    private final AbstractStorage storage;
    private final int capacity;
    private int size;

    public FixCapacityStorage(AbstractStorage storage, int capacity) {
        this.storage = storage;
        this.capacity = capacity;
        this.size = super.foods.size();
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
