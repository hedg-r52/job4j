package lsp.warehouses;

import lsp.foods.Food;
import lsp.foods.IFood;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract storage of food
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public abstract class AbstractStorage {
    protected List<IFood> foods = new ArrayList<>();

    public boolean add(IFood food) {
        return foods.add(food);
    }

    public abstract boolean isSuitable(IFood food, Date currentDate);
}
