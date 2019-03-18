package lsp.foods;

import lsp.foods.decorator.ColdStoreDecorator;
import java.util.Date;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public class ColdStoreFood extends ColdStoreDecorator {
    public ColdStoreFood(IFood food) {
        super(food);
    }

    @Override
    public boolean needColdStorage() {
        return true;
    }

    @Override
    public boolean canReproduct() {
        return food.canReproduct();
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
