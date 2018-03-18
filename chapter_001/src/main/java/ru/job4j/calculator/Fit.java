package ru.job4j.calculator;

/**
 * Программа расчета идеального веса.
 */
public class Fit {

    private static final double COEFFICIENT = 1.15D;
    private static final int MALE_RATE = 100;
    private static final int FEMALE_RATE = 110;

    /**
     * Идеальный вес для мужщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double manWeight(double height) {
        return (height - MALE_RATE) * COEFFICIENT;
    }

    /**
     * Идеальный вес для женщины.
     * @param height Рост.
     * @return идеальный вес.
     */
    public double womanWeight(double height) {
        return (height - FEMALE_RATE) * COEFFICIENT;
    }
}