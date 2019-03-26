package lsp.warehouses;

import lsp.foods.IFood;

import java.util.Date;
import java.util.List;

public interface IStorage {
    boolean add(IFood food);
    boolean isSuitable(IFood food, Date currentDate);
    int getSize();
    List<IFood> getAllFood();
    void clean();
}
