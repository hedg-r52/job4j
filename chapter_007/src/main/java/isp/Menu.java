package isp;

import ru.job4j.utils.generators.StringGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Menu
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Menu {
    private final static String LN = System.getProperty("line.separator");
    private final static StringGenerator GEN = new StringGenerator();
    private final static char SYMBOL = '-';
    private List<MenuItem> items = new ArrayList<>();

    /**
     * Inner class for stack with level
     */
    class StackItem {
        private final MenuItem menuItem;
        private final int level;

        public StackItem(MenuItem menuItem, int level) {
            this.menuItem = menuItem;
            this.level = level;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getLevel() {
            return level;
        }
    }

    /**
     * add item at collection
     *
     * @param item
     */
    public void add(MenuItem item) {
        this.items.add(item);
    }

    /**
     * String representation of menu
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Stack<StackItem> stack = new Stack<>();
        List<MenuItem> items = this.items;
        Collections.reverse(items);
        for (MenuItem i : items) {
            stack.push(new StackItem(i, 0));
        }
        while (stack.size() != 0) {
            StackItem stackItem = stack.pop();
            sb.append(this.getMenuItemRepresentation(stackItem.getMenuItem().toString(), stackItem.getLevel()));
            List<MenuItem> childs = stackItem.getMenuItem().getChilds();
            Collections.reverse(childs);
            for (MenuItem child : childs) {
                stack.push(new StackItem(child, stackItem.getLevel() + 1));
            }
        }
        return sb.toString();
    }

    /**
     * Representation of text line of menu item
     * @param name name of menu item
     * @param level level for treelike formatting
     * @return string representation
     */
    private String getMenuItemRepresentation(String name, int level) {
        return String.format(
                "+%s %s%s",
                GEN.getCharSequence(SYMBOL, level * 2),
                name,
                LN
        );
    }
}
