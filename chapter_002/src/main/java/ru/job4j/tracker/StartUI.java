package ru.job4j.tracker;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {

    private static final int ADD_ITEM = 0;
    private static final int SHOW_ALL_ITEMS = 1;
    private static final int EDIT_ITEM = 2;
    private static final int DELETE_ITEM = 3;
    private static final int FIND_ITEM_BY_ID = 4;
    private static final int FIND_ITEMS_BY_NAME = 5;
    private static final int EXIT = 6;

    private final Input input;
    private final Tracker tracker;

    /**
     * Конструктор, инициализирующий поля.
     * @param input ввод данных
     * @param tracker хранилище заявок
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы
     */
    public void init() {
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            switch (Integer.valueOf(answer)) {
                case ADD_ITEM:
                    this.createItem();
                    break;
                case SHOW_ALL_ITEMS:
                    this.showAllItems();
                    break;
                case EDIT_ITEM:
                    this.replaceItem();
                    break;
                case DELETE_ITEM:
                    this.deleteItem();
                    break;
                case FIND_ITEM_BY_ID:
                    this.findItemById();
                    break;
                case FIND_ITEMS_BY_NAME:
                    this.findItemsByName();
                    break;
                case EXIT:
                    exit = true;
                    break;
                default:
            }
        }
    }

    /**
     * Показать меню
     */
    private void showMenu() {
        System.out.println("Меню");
        System.out.println("0. Добавить новую заявку");
        System.out.println("1. Показать все заявки");
        System.out.println("2. Редактировать заявку");
        System.out.println("3. Удалить заявку");
        System.out.println("4. Найти заявку по ID");
        System.out.println("5. Найти заявки по имени");
        System.out.println("6. Выход из программы");
    }

    /**
     * Создание заявки
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки --------------");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с id : " + item.getId() + "-----------");
    }

    /**
     * Показать все заявки
     */
    private void showAllItems() {
        System.out.println("------------ Список заявок --------------");
        Item[] items = tracker.findAll();
        for (Item item : items) {
            showItem(item);
        }
        System.out.println("------------ Конец списка заявок --------------");
    }

    private void showItem(Item item) {
        System.out.println("ID : " + item.getId() + "  Заявка: " + item.getName());
        System.out.println("Описание : ");
        System.out.println(item.getDesc());
        System.out.println("------------");
        System.out.println();
    }

    /**
     * Изменить заявку
     */
    private void replaceItem() {
        System.out.println("------------ Изменение заявки --------------");
        String id = this.input.ask("Введите ID заявки:");
        String name = this.input.ask("Введите имя заявки :");
        String desc = this.input.ask("Введите описание заявки :");
        Item item = new Item(name, desc);
        this.tracker.replace(id, item);
        System.out.println("------------ Заявка с id : " + item.getId() + " изменена -----------");
    }

    /**
     * Удалить заявку
     */
    private void deleteItem() {
        System.out.println("------------ Удаление заявки --------------");
        String id = this.input.ask("Введите ID заявки:");
        if (tracker.findById(id) != null) {
            tracker.delete(id);
            System.out.println("------------ Заявка с id : " + id + " удалена -----------");
        } else {
            System.out.println("------------ Заявка с id : " + id + " не найдена -----------");
        }
    }

    /**
     * Найти заявку по id
     */
    private void findItemById() {
        System.out.println("------------ Вывод заявки по id --------------");
        String id = this.input.ask("Введите ID заявки:");
        Item findedItem = tracker.findById(id);
        if (findedItem != null) {
            showItem(findedItem);
        } else {
            System.out.println("------------ Заявка с id : " + id + " не найдена -----------");
        }
    }

    /**
     * Найти заявки по имени
     */
    private void findItemsByName() {
        System.out.println("------------ Вывод заявки по id --------------");
        String key = this.input.ask("Введите имя заявки:");
        Item[] findedItems = tracker.findByName(key);
        if (findedItems.length > 0) {
            for (Item item : findedItems) {
                showItem(item);
            }
        } else {
            System.out.println("------------ Заявок не найдено -----------");
        }
    }

    /**
     * Запуск программы
     * @param args аргументы
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
