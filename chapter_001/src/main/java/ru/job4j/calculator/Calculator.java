package ru.job4j.calculator;

/**
 * Calculator
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Calculator {
    double result;

    /**
     * Addition
     * @param first
     * @param second
     */
    public void add(double first, double second) {
        this.result = first + second;
    }

    /**
     * Subtraction
     * @param first
     * @param second
     */
    public void subtract(double first, double second) {
        this.result = first - second;
    }

    /**
     * Division
     * @param first
     * @param second
     */
    public void div(double first, double second) {
        this.result = first / second;
    }

    /**
     * Multiplying
     * @param first
     * @param second
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }


    public double getResult() {
        return this.result;
    }
}
