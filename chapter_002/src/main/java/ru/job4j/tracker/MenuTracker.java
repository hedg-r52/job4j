package ru.job4j.tracker;

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
        this.actions[0] = new MenuTracker.AddItemAction();
        this.actions[1] = new ShowAllItemsAction();
        this.actions[2] = this.new EditItemAction();
        this.actions[3] = new MenuTracker.DeleteItemAction();
        this.actions[4] = new MenuTracker.FindItemByIdAction();
        this.actions[5] = new MenuTracker.FindItemsByNameAction();
        this.actions[6] = new MenuTracker.ExitAction(this);
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

    private static class AddItemAction implements UserAction {
        @Override
        public int key() {
            return 0;
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

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Add the new item.");
        }
    }

    private class EditItemAction implements UserAction {
        @Override
        public int key() {
            return 2;
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

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Edit item.");
        }
    }

    private static class DeleteItemAction implements UserAction {
        @Override
        public int key() {
            return 3;
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

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Delete item.");
        }
    }

    private static class FindItemByIdAction implements UserAction {
        @Override
        public int key() {
            return 4;
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

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Find item by id.");
        }
    }

    private static class FindItemsByNameAction implements UserAction {
        @Override
        public int key() {
            return 5;
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

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Find items by name.");
        }
    }

    private static class ExitAction implements UserAction {
        private MenuTracker outer;

        ExitAction(MenuTracker outer) {
            this.outer = outer;
        }

        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
             outer.setExit(true);
        }

        @Override
        public String info() {
            return String.format("%s. %s", this.key(), "Exit Program.");
        }
    }
}

class ShowAllItemsAction implements UserAction {
    @Override
    public int key() {
        return 1;
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Список заявок --------------");
        for (Item item : tracker.findAll()) {
            System.out.println(item);
        }
        System.out.println("------------ Конец списка заявок --------------");
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key(), "Show all items.");
    }
}