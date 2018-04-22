package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Телефонный справочник
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class PhoneDictionary {
    private List<Person> persons = new ArrayList<Person>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подощедщих пользователей.
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (Person person : this.persons) {
            String str = String.format(
                    "%s, %s, %s, %s.",
                    person.getSurname(),
                    person.getName(),
                    person.getAddress(),
                    person.getPhone()
            );
            if (str.contains(key)) {
                result.add(person);
            }
        }
        return result;
    }
}