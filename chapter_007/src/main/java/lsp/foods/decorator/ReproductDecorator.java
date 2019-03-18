package lsp.foods.decorator;

import lsp.foods.IFood;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 18.03.2019
 * @version 0.1
 */
public abstract class ReproductDecorator implements IFood {
    protected IFood food;

    public ReproductDecorator(IFood food) {
        this.food = food;
    }

    @Override
    public boolean canReproduct() {
        return food.canReproduct();
    }
}
