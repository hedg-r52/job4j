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
    protected final Calculator calc;
    private final Scanner scanner;
    protected Double result;
    protected Map<String, BiConsumer<Double, Double>> operations = new HashMap<>();

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
    protected void initOperations() {
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
    protected void load(String op, BiConsumer<Double, Double> handle) {
        this.operations.put(op, handle);
    }

    /**
     * Handle of adding
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleAdd() {
        return calc::add;
    }

    /**
     * Handle of subtracting
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleSub() {
        return calc::subtract;
    }

    /**
     * Handle of division
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleDiv() {
        return calc::div;
    }

    /**
     * Handle of multiplying
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleMul() {
        return calc::multiple;
    }

    /**
     * Execution expression
     * @param expression input expression
     *
     *  Split expression by space and get three parts
     */
    public boolean execute(String expression) {
        boolean valid = this.isValid(expression);
        if (valid) {
            String[] parts = expression.split(" ");
            this.operations.get(parts[1]).accept(
                    Double.valueOf(parts[0]),
                    Double.valueOf(parts[2])
            );
            this.result = calc.getResult();
        }
        return valid;
    }

    /**
     * print  result to console
     */
    private void printResult(boolean valid) {
        if (valid) {
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
    protected boolean isValid(String expression) {
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
            boolean valid = this.execute(input);
            this.printResult(valid);
            input = scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        InteractCalculator iCalc = new InteractCalculator();
        iCalc.run();
    }
}
