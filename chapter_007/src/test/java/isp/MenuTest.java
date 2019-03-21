package isp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Menu test
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MenuTest {
    private final static String LN = System.getProperty("line.separator");
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream ps = new PrintStream(out);
    private Menu menu;
    private MenuItem task1, task1p1, task1p1p1, task1p1p2, task1p2;

    @Before
    public void setUp() throws Exception {
        menu = new Menu();
        task1 = new MenuItem("task 1");
        task1p1 = new MenuItem("task 1.1");
        task1p1p1 = new MenuItem("task 1.1.1");
        task1p1p2 = new MenuItem("task 1.1.2");
        task1p2 = new MenuItem("task 1.2");

        task1p1.addItem(task1p1p1);
        task1p1.addItem(task1p1p2);
        task1.addItem(task1p1);
        task1.addItem(task1p2);
        menu.add(task1);
        System.setOut(ps);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(stdout);
    }

    @Test
    public void whenCreateMenu() {
        String expected = "+ task 1" + LN
                + "+-- task 1.1" + LN
                + "+---- task 1.1.1" + LN
                + "+---- task 1.1.2" + LN
                + "+-- task 1.2" + LN + LN;
        assertThat(menu.toString(), is(expected));
    }

    @Test
    public void whenMenuItemAction() {
        String expected = "Action from \"task 1.1\" menu item" + LN;
        task1p1.action();
        assertThat(out.toString(), is(expected));
    }
}