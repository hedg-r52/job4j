package lsp.foods;

import lsp.foods.decorator.ReproductDecorator;
import java.util.Date;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class ReproductFood extends ReproductDecorator {

    public ReproductFood(Food food) {
        super(food);
    }

    @Override
    public boolean needColdStorage() {
        return false;
    }

    @Override
    public boolean canReproduct() {
        return true;
    }

    @Override
    public int getDaysOfLifeInPercent(Date currentDate) {
        return food.getDaysOfLifeInPercent(currentDate);
    }

    @Override
    public float getPrice() {
        return food.getPrice();
    }

    @Override
    public void enableSale() {
        food.enableSale();
    }
}
