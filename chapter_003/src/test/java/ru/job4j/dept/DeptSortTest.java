package ru.job4j.dept;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тесты класса DeptSort
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class DeptSortTest {

    @Test
    public void whenGetUnsortedListThenSortByDept() {
        DeptSort deptSort = new DeptSort();
        List<Org> depts = new ArrayList<>();
        depts.add(new Org("K1\\S2"));
        depts.add(new Org("K1\\S1\\D13"));
        depts.add(new Org("K2\\S2"));
        List<Org> result = deptSort.sort(depts);
        List<Org> expected = new ArrayList<>();
        expected.add(new Org("K1"));
        expected.add(new Org("K1\\S1"));
        expected.add(new Org("K1\\S1\\D13"));
        expected.add(new Org("K1\\S2"));
        expected.add(new Org("K2"));
        expected.add(new Org("K2\\S2"));
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetFourNodeStringWhenSetFourNodes() {
        DeptSort deptSort = new DeptSort();
        List<Org> depts = new ArrayList<>();
        depts.add(new Org("K1\\S2\\D11\\M23"));
        List<Org> result = deptSort.appendAllNodes(depts);
        assertThat(result.size(), is(4));
    }
}
