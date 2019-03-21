package isp;

import ru.job4j.utils.generators.StringGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 * Menu
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Menu {
    private final static String LN = System.getProperty("line.separator");
    private final static char SYMBOL = '-';
    private List<MenuItem> items;

    /**
     * Constructor
     */
    public Menu() {
        this.items = new ArrayList<>();
    }

    /**
     * add item at collection
     * @param item
     */
    public void add(MenuItem item) {
        this.items.add(item);
    }

    /**
     * String representation of menu
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuItem i : this.items) {
            sb.append(getChilds(i, 0) + LN);
        }
        return sb.toString();
    }

    /**
     * recursive method for get string representation all levels of menu
     *
     * @param item
     * @param level current level
     * @return string representation of branch
     */
    private String getChilds(MenuItem item, int level) {
        StringGenerator generator = new StringGenerator(level * 2);
        String result = String.format(
                "+%s %s%s",
                generator.getCharSequence(SYMBOL),
                item.toString(),
                LN
        );
        for (MenuItem i : item.getChilds()) {
            result += getChilds(i, level + 1);
        }
        return result;
    }
}
