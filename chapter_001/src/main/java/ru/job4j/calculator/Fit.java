package ru.job4j.calculator;

/**
 * Программа расчета идеального веса.
 */
public class Fit {

    private static final double COEFFICIENT = 1.15D;
    /**
     * Идеальный вес для мужщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double manWeight(double height) {
        return (height - 100) * COEFFICIENT;
    }

    /**
     * Идеальный вес для женщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double womanWeight(double height) {
        return (height - 110) * COEFFICIENT;
    }
}