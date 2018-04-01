package ru.job4j.tracker;

import java.util.Arrays;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private UserAction[] actions = new UserAction[7];
    private boolean exit;

    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        exit = false;
    }

    public boolean isExit() {
        return this.exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void fillActions() {
        this.actions[0] = new MenuTracker.AddItemAction(0, "Add the new item.");
        this.actions[1] = new ShowAllItemsAction(1, "Show all items.");
        this.actions[2] = this.new EditItemAction(2, "Edit item.");
        this.actions[3] = new MenuTracker.DeleteItemAction(3, "Delete item.");
        this.actions[4] = new MenuTracker.FindItemByIdAction(4, "Find item by id.");
        this.actions[5] = new MenuTracker.FindItemsByNameAction(5, "Find items by name.");
        this.actions[6] = new MenuTracker.ExitAction(6, "Exit Program.", this);
    }

    /**
     * Возвращает массив с пунктами меню
     *  устанавливаем длину результирующего массива равной
     *  длине массива actions, по циклу проходим все элементы actions
     *  и если элемент массива не пустой то добавляем в результирующий
     *  перед возвратом возвращаем только найденное количество элементов
     * @return массив с доступными пунктами меню
     */
    public int[] getRange() {
        int[] result = new int[this.actions.length];
        int count = 0;
        for (int i = 0; i < this.actions.length; i++) {
            if (actions[i] != null) {
                result[count++] = actions[i].key();
            }
        }
        return Arrays.copyOf(result, count);
    }

    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
    }

    public void show() {
        for (UserAction action : actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    private static class AddItemAction extends BaseAction {

        public AddItemAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки --------------");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("------------ Новая заявка с id : " + item.getId() + "-----------");
        }

    }

    private class EditItemAction extends BaseAction {
        public EditItemAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Изменение заявки --------------");
            String id = input.ask("Введите ID заявки:");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            tracker.replace(id, item);
            System.out.println("------------ Заявка с id : " + item.getId() + " изменена -----------");
        }
    }

    private static class DeleteItemAction extends BaseAction {
        public DeleteItemAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String id = input.ask("Введите ID заявки:");
            if (tracker.findById(id) != null) {
                tracker.delete(id);
                System.out.println("------------ Заявка с id : " + id + " удалена -----------");
            } else {
                System.out.println("------------ Заявка с id : " + id + " не найдена -----------");
            }
        }
    }

    private static class FindItemByIdAction extends BaseAction {
        public FindItemByIdAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Вывод заявки по id --------------");
            String id = input.ask("Введите ID заявки:");
            Item findedItem = tracker.findById(id);
            if (findedItem != null) {
                System.out.println(findedItem);
            } else {
                System.out.println("------------ Заявка с id : " + id + " не найдена -----------");
            }
        }
    }

    private static class FindItemsByNameAction extends BaseAction {
        public FindItemsByNameAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Вывод заявки по id --------------");
            String key = input.ask("Введите имя заявки:");
            Item[] findedItems = tracker.findByName(key);
            if (findedItems.length > 0) {
                for (Item item : findedItems) {
                    System.out.println(item);
                }
            } else {
                System.out.println("------------ Заявок не найдено -----------");
            }
        }
    }

    private static class ExitAction extends BaseAction {
        private MenuTracker outer;

        ExitAction(int key, String name) {
            super(key, name);
        }

        ExitAction(int key, String name, MenuTracker outer) {
            this(key, name);
            this.outer = outer;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
             outer.setExit(true);
        }
    }
}

class ShowAllItemsAction extends BaseAction {
    public ShowAllItemsAction(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Список заявок --------------");
        for (Item item : tracker.findAll()) {
            System.out.println(item);
        }
        System.out.println("------------ Конец списка заявок --------------");
    }
}