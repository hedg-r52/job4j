package ru.job4j.tracker;

import java.util.List;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    private final Input input;
    private final Tracker tracker;
    private List<Integer> ranges;
    private boolean exit;

    /**
     * Конструктор, инициализирующий поля.
     * @param input ввод данных
     * @param tracker хранилище заявок
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
        this.exit = false;
    }

    /**
     * Основной цикл программы
     */
    public void init() {
        MenuTracker menu = new MenuTracker(input, tracker);
        menu.fillActions();
        ranges = menu.getRange();
        do {
            menu.show();
            menu.select(input.ask("Select:", ranges));
        } while (!menu.isExit());
    }

    /**
     * Запуск программы
     * @param args аргументы
     */
    public static void main(String[] args) {
        new StartUI(
                new ValidateInput(
                    new ConsoleInput()
                ),
                new Tracker()
        ).init();
    }
}
