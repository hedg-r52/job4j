package ru.job4j.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

/**
 * Interact Calculator
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class InteractCalculator {
    private final Calculator calc;
    private final Scanner scanner;
    private Double result;
    private boolean valid;
    private Map<String, BiConsumer<Double, Double>> operations = new HashMap<>();

    /**
     * Constructor
     *
     */
    public InteractCalculator() {
        this.scanner = new Scanner(System.in);
        this.calc = new Calculator();
        this.initOperations();
    }

    /**
     * Init calculator's operations
     */
    private void initOperations() {
        this.load("+", handleAdd());
        this.load("-", handleSub());
        this.load("/", handleDiv());
        this.load("*", handleMul());
    }

    /**
     * Load operation at  collection
     * @param op
     * @param handle
     */
    private void load(String op, BiConsumer<Double, Double> handle) {
        this.operations.put(op, handle);
    }

    /**
     * Handle of adding
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleAdd() {
        return (first, second) -> {
            calc.add(first, second);
            this.result = calc.getResult();
        };
    }

    /**
     * Handle of subtracting
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleSub() {
        return (first, second) -> {
            calc.subtract(first, second);
            this.result = calc.getResult();
        };
    }

    /**
     * Handle of division
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleDiv() {
        return (first, second) -> {
            calc.div(first, second);
            this.result = calc.getResult();
        };
    }

    /**
     * Handle of multiplying
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleMul() {
        return (first, second) -> {
            calc.multiple(first, second);
            this.result = calc.getResult();
        };
    }

    /**
     * Execution expression
     * @param expression input expression
     *
     *  Split expression by space and get three parts
     */
    public void execute(String expression) {
        this.valid = this.isValid(expression);
        if (this.valid) {
            String[] parts = expression.split(" ");
            this.operations.get(parts[1]).accept(
                    Double.valueOf(parts[0]),
                    Double.valueOf(parts[2])
            );
        }
    }

    /**
     * print  result to console
     */
    private void printResult() {
        if (this.valid) {
            System.out.println(this.result);
        } else {
            System.out.println("Wrong expression!");
        }
    }

    /**
     * Checks expression on correct input
     * @param expression
     * @return
     */
    private boolean isValid(String expression) {
        boolean result = true;
        String[] parts = expression.split(" ");
        result = result && (parts.length == 3);
        result = result && (this.operations.containsKey(parts[1]));
        return result;
    }

    /**
     * Main loop
     */
    public void run() {
        String input = scanner.nextLine();
        while (!"q".equals(input)) {
            this.execute(input);
            this.printResult();
            input = scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        InteractCalculator iCalc = new InteractCalculator();
        iCalc.run();
    }
}
