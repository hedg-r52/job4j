package ru.job4j.calculator;

import java.util.function.BiConsumer;

public class EngineerCalculator extends InteractCalculator {

    @Override
    protected void initOperations() {
        super.initOperations();
        load("sin", handleSin());
        load("cos", handleCos());
    }

    /**
     * Handle of sin
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleSin() {
        return (first, second) -> {
            this.result = Math.sin(first);
        };
    }

    /**
     * Handle of cos
     * @return BiConsumer
     */
    private BiConsumer<Double, Double> handleCos() {
        return (first, second) -> {
            this.result = Math.cos(first);
        };
    }

    @Override
    public boolean execute(String expression) {
        boolean valid = this.isValid(expression);
        if (valid) {
            String[] parts = expression.split(" ");
            Double left = Double.valueOf(parts[0]);
            Double right = (parts.length == 2 ?  0d : Double.valueOf(parts[2]));
            String op = parts[1];
            this.operations.get(op).accept(left, right);
        }
        return valid;
    }

    @Override
    protected boolean isValid(String expression) {
        boolean result = true;
        String[] parts = expression.split(" ");
        result = result && (parts.length > 1);
        result = result && (this.operations.containsKey(parts[1]));
        return result;
    }

    public static void main(String[] args) {
        EngineerCalculator eCalc = new EngineerCalculator();
        eCalc.run();
    }
}
