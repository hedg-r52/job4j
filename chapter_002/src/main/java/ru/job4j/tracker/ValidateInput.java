package ru.job4j.tracker;

import java.util.List;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ValidateInput implements Input {

    private final Input input;

    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException ex) {
                System.out.println("Please select key from menu.");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter validate data again.");
            }
        } while (invalid);
        return value;
    }
}
