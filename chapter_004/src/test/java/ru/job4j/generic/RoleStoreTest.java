package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест класса RoleStore
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RoleStoreTest {
    private RoleStore rs;

    @Before
    public void setUp() {
        rs = new RoleStore(10);
        rs.add(new Role("1"));
    }

    @Test
    public void whenFindByIdGetExpectedRoleResult() {
        Role expected = new Role("2");
        rs.add(expected);
        Role result = rs.findById("2");
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindByIdGetNullResult() {
        Role result = rs.findById("3");
        assertNull(result);
    }

    @Test
    public void whenReplaceRoleThenOldRoleNotFoundAndNewRoleFound() {
        Role expected = new Role("5");
        rs.replace("1", expected);
    }

    @Test
    public void whenDeleteThenResultTrueAndFindByIdIsNull() {
        assertThat(rs.delete("1"), is(true));
        assertNull(rs.findById("1"));
    }
}
