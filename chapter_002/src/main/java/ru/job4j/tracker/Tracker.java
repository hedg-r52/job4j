package ru.job4j.tracker;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Tracker {
    private final Item[] items = new Item[100];
    private int position = 0;
    private static final Random RN = new Random();

    /**
     * Добавление заявки
     * @param item новая заявка
     * @return ссылка на созданную заявку
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items[this.position++] = item;
        return item;
    }

    /**
     * Редактирование заявки (замена заявки на новую)
     * @param id Уникальный ключ заявки
     * @param item новая заявка
     */
    public void replace(String id, Item item) {
        for (int i = 0; i < position; i++) {
            if (items[i].getId().equals(id)) {
                items[i] = item;
                items[i].setId(id);
                break;
            }
        }
    }

    /**
     * Удаление заявки и сдвиг остальных элементов
     * @param id Уникальный ключ заявки
     */
    public void delete(String id) {
        boolean findId = false;
        for (int i = 0; i < position; i++) {
            if (findId) {
                this.items[i - 1] = items[i];
            } else {
                if (items[i].getId().equals(id)) {
                    findId = true;
                }
            }
        }
        items[--position] = null;
    }

    /**
     * Получить список всех заявок
     * @return массив заявок
     */
    public Item[] findAll() {
        return Arrays.copyOf(items, position);
    }

    /**
     * Получить список заявок по имени
     * @param key Имя для поиска
     * @return массив заявок
     */
    public Item[] findByName(String key) {
        Item[] findedItems = new Item[position];
        int count = 0;
        for (int i = 0; i < position; i++) {
            if (items[i].getName().equals(key)) {
                findedItems[count++] = items[i];
            }
        }
        return Arrays.copyOf(findedItems, count);
    }

    /**
     * Получить заявку по id
     * @param id Уникальный идентификатор
     * @return Заявка
     */
    public Item findById(String id) {
        Item result = null;
        for (int i = 0; i < this.position; i++) {
            if (items[i].getId().equals(id)) {
                result = items[i];
                break;
            }
        }
        return result;
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