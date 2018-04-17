package ru.job4j.sort;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Тестирование класса сортировки пользователей
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SortUserTest {
    /**
     * Создаем пользователей
     * Помещаем в list в произвольном порядке
     * Сортируем в result
     * Сравниваем с expected - сортированный при инициализации массив
     */
    @Test
    public void whenNotSortedListThenSortedList() {
        User andrei = new User("Andrei", 36);
        User nikita = new User("Nikita", 34);
        User oleg = new User( "Oleg", 40);
        User nikolai = new User("Nikolai", 27);
        List<User> list = new ArrayList<User>();
        list.add(andrei);
        list.add(nikita);
        list.add(oleg);
        list.add(nikolai);
        User[] result = new SortUser().sort(list).toArray(new User[0]);
        User[] expected = new User[] {nikolai, nikita, andrei, oleg};
        assertThat(result, is(expected));
    }
}
