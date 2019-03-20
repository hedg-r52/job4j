package lsp.warehouses;

import lsp.foods.IFood;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Storage
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 20.03.2019
 * @version 0.1
 */
public class Storage implements IStorage {
    protected List<IFood> foods = new ArrayList<>();

    @Override
    public boolean add(IFood food) {
        return foods.add(food);
    }

    @Override
    public boolean isSuitable(IFood food, Date currentDate) {
        return false;
    }

    @Override
    public int getSize() {
        return this.foods.size();
    }
}
