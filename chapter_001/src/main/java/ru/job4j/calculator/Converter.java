package ru.job4j.calculator;

/**
 * Конвертор валюты.
 */
public class Converter {

    private static final int DOLLAR_TO_RUBLE_RATE = 60;
    private static final int EURO_TO_RUBLE_RATE = 70;

    /**
     * Конвертируем рубли в евро.
     * @param value рубли.
     * @return евро.
     */
    public int rubleToEuro(int value) {
        return value / EURO_TO_RUBLE_RATE;
    }

    /**
     * Конвертируем рубли в доллары.
     * @param value рубли.
     * @return доллары.
     */
    public int rubleToDollar(int value) {
        return value / DOLLAR_TO_RUBLE_RATE;
    }

    /**
     * Конвертируем евро в рубли.
     * @param value евро.
     * @return рубли.
     */
    public int euroToRuble(int value) {
        return value * EURO_TO_RUBLE_RATE;
    }

    /**
     * Конвертируем доллары в рубли.
     * @param value доллары.
     * @return рубли.
     */
    public int dollarToRuble(int value) {
        return value * DOLLAR_TO_RUBLE_RATE;
    }

}
