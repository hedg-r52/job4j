package isp;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu item
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MenuItem implements IMenuItem {
    private final static String LN = System.getProperty("line.separator");
    private String name;
    private Event event = new SimpleEvent();
    private List<MenuItem> childs = new ArrayList<>();

    /**
     * Constructor
     * @param name menu item name
     */
    public MenuItem(String name) {
        this.name = name;
    }

    /**
     * Adding childs for current item
     * @param item child item
     */
    public void addItem(MenuItem item) {
        this.childs.add(item);
    }

    /**
     * Get list of childs
     * @return list of childs
     */
    public List<MenuItem> getChilds() {
        return this.childs;
    }

    /**
     * Action of menu item
     */
    @Override
    public void action() {
        event.setMessage(String.format("Action from \"%s\" menu item%s", name, LN));
    }

    /**
     * Returns event message
     * @return event message
     */
    @Override
    public String getMessage() {
        return event.message();
    }

    /**
     * String representation of menu item
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
}
