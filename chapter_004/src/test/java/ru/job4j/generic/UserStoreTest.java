package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тест класса UserStore
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {
    private UserStore rs;

    @Before
    public void setUp() {
        rs = new UserStore(10);
        rs.add(new User("1"));
    }

    @Test
    public void whenFindByIdGetExpectedRoleResult() {
        User expected = new User("2");
        rs.add(expected);
        User result = rs.findById("2");
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindByIdGetNullResult() {
        User result = rs.findById("3");
        assertNull(result);
    }

    @Test
    public void whenReplaceRoleThenOldRoleNotFoundAndNewRoleFound() {
        User expected = new User("5");
        rs.replace("1", expected);
    }

    @Test
    public void whenDeleteThenResultTrueAndFindByIdIsNull() {
        assertThat(rs.delete("1"), is(true));
        assertNull(rs.findById("1"));
    }
}
