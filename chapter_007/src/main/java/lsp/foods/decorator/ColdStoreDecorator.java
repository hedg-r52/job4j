package lsp.foods.decorator;

import lsp.foods.IFood;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public abstract class ColdStoreDecorator implements IFood {
    protected IFood food;

    public ColdStoreDecorator(IFood food) {
        this.food = food;
    }

    @Override
    public boolean needColdStorage() {
        return food.needColdStorage();
    }
}
