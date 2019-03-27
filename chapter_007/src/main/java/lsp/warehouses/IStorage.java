package lsp.warehouses;

import lsp.foods.IFood;
import java.util.Date;
import java.util.List;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface IStorage {
    boolean add(IFood food);
    boolean isSuitable(IFood food, Date currentDate);
    int getSize();
    List<IFood> getAllFood();
    void clean();
}
