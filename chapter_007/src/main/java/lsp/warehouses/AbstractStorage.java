package lsp.warehouses;

import lsp.foods.Food;
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
    protected List<Food> foods = new ArrayList<>();

    public boolean add(Food food) {
        return foods.add(food);
    }

    public abstract boolean isSuitable(Food food, Date currentDate);
}
