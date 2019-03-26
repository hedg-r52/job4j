package lsp.warehouses.decorator;

import lsp.foods.IFood;
import lsp.warehouses.IStorage;

import java.util.List;

/**
 * Storage decorator
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.03.2019
 * @version 0.1
 */
public abstract class StorageDecorator implements IStorage {
    protected IStorage storage;

    public StorageDecorator(IStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean add(IFood food) {
        return this.storage.add(food);
    }

    @Override
    public int getSize() {
        return this.storage.getSize();
    }

    @Override
    public List<IFood> getAllFood() {
        return this.storage.getAllFood();
    }

    @Override
    public void clean() {
        this.storage.clean();
    }
}
