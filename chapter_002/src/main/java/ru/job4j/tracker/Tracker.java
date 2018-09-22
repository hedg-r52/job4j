package ru.job4j.tracker;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    private final List<Item> items = new ArrayList<>();
    private int position = 0;
    private static final Random RN = new Random();

    /**
     * Добавление заявки
     * @param item новая заявка
     * @return ссылка на созданную заявку
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Редактирование заявки (замена заявки на новую)
     * @param id Уникальный ключ заявки
     * @param newItem новая заявка
     */
    public void replace(String id, Item newItem) {
        for (int i = 0; i < this.items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                newItem.setId(id);
                items.set(i, newItem);
                break;
            }
        }
    }

    /**
     * Удаление заявки и сдвиг остальных элементов
     * @param id Уникальный ключ заявки
     */
    public void delete(String id) {
        for (Item item : this.items) {
            if (item.getId().equals(id)) {
                items.remove(item);
                break;
            }
        }
    }

    /**
     * Получить список всех заявок
     * @return массив заявок
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Получить список заявок по имени
     * @param key Имя для поиска
     * @return массив заявок
     */
    public List<Item> findByName(String key) {
        Predicate<Item> searchByKey = p -> p.getName().equals(key);
        List<Item> findedItems = new ArrayList<>();
        this.items.stream().filter(searchByKey).forEach(
                p -> findedItems.add(p)
        );
        return findedItems;
    }

    /**
     * Получить заявку по id
     * @param id Уникальный идентификатор
     * @return Заявка
     */
    public Item findById(String id) {
        Predicate<Item> searchById = p -> p.getId().equals(id);
        Optional<Item> foundItem = this.items.stream().findFirst().filter(searchById);
        if (foundItem.isPresent()) {
            return foundItem.get();
        } else {
            return null;
        }
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }
}