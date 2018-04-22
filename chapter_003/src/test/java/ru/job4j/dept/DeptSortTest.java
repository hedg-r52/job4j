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
        List<String> depts = new ArrayList<>();
        depts.add("K1\\S2");
        depts.add("K1\\S1\\D13");
        depts.add("K2\\S2");
        List<String> result = deptSort.sort(depts);
        List<String> expected = new ArrayList<>();
        expected.add("K1");
        expected.add("K1\\S1");
        expected.add("K1\\S1\\D13");
        expected.add("K1\\S2");
        expected.add("K2");
        expected.add("K2\\S2");
        assertThat(result, is(expected));
    }

    @Test
    public void whenGetFourNodeStringWhenSetFourNodes() {
        DeptSort deptSort = new DeptSort();
        List<String> depts = new ArrayList<>();
        depts.add("K1\\S2\\D11\\M23");
        List<String> result = deptSort.appendAllNodes(depts);
        assertThat(result.size(), is(4));
    }
}
