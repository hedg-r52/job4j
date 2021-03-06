package ru.job4j.jdbc.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MenuTracker {

    private Input input;
    private TrackerSQL tracker;
    private List<UserAction> actions = new ArrayList<>();
    private boolean exit;

    public MenuTracker(Input input, TrackerSQL tracker) {
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
        actions.add(new MenuTracker.AddItemAction(0, "Add the new item."));
        actions.add(new ShowAllItemsAction(1, "Show all items."));
        actions.add(this.new EditItemAction(2, "Edit item."));
        actions.add(new MenuTracker.DeleteItemAction(3, "Delete item."));
        actions.add(new MenuTracker.FindItemByIdAction(4, "Find item by id."));
        actions.add(new MenuTracker.FindItemsByNameAction(5, "Find items by name."));
        actions.add(new MenuTracker.ExitAction(6, "Exit Program.", this));
    }

    /**
     * Возвращает массив с пунктами меню
     * устанавливаем длину результирующего массива равной
     * длине массива actions, по циклу проходим все элементы actions
     * и если элемент массива не пустой то добавляем в результирующий
     * перед возвратом возвращаем только найденное количество элементов
     *
     * @return массив с доступными пунктами меню
     */
    public List<Integer> getRange() {
        List<Integer> result = new ArrayList<>();
        int count = 0;
        for (UserAction action : this.actions) {
            if (action != null) {
                result.add(action.key());
            }
        }
        return result;
    }

    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
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
        public void execute(Input input, TrackerSQL tracker) {
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
        public void execute(Input input, TrackerSQL tracker) {
            System.out.println("------------ Изменение заявки --------------");
            String id = input.ask("Введите ID заявки:");
            String name = input.ask("Введите имя заявки :");
            String desc = input.ask("Введите описание заявки :");
            Item item = new Item(name, desc);
            tracker.replace(Integer.valueOf(id), item);
            System.out.println("------------ Заявка с id : " + item.getId() + " изменена -----------");
        }
    }

    private static class DeleteItemAction extends BaseAction {
        public DeleteItemAction(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, TrackerSQL tracker) {
            System.out.println("------------ Удаление заявки --------------");
            String id = input.ask("Введите ID заявки:");
            if (tracker.findById(Integer.valueOf(id)) != null) {
                tracker.delete(Integer.valueOf(id));
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
        public void execute(Input input, TrackerSQL tracker) {
            System.out.println("------------ Вывод заявки по id --------------");
            String id = input.ask("Введите ID заявки:");
            Item foundItem = tracker.findById(Integer.valueOf(id));
            if (foundItem != null) {
                System.out.println(foundItem);
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
        public void execute(Input input, TrackerSQL tracker) {
            System.out.println("------------ Вывод заявки по id --------------");
            String key = input.ask("Введите имя заявки:");
            List<Item> foundItems = tracker.findByName(key);
            if (foundItems.size() > 0) {
                for (Item item : foundItems) {
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
        public void execute(Input input, TrackerSQL tracker) {
            outer.setExit(true);
        }
    }
}

class ShowAllItemsAction extends BaseAction {
    public ShowAllItemsAction(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, TrackerSQL tracker) {
        System.out.println("------------ Список заявок --------------");
        for (Item item : tracker.findAll()) {
            System.out.println(item);
        }
        System.out.println("------------ Конец списка заявок --------------");
    }
}