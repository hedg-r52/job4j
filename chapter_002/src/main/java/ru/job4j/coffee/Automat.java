package ru.job4j.coffee;

import java.util.Arrays;

public class Automat {
    private int[] nominals;

    public Automat(int[] nominals, int coffeePrice) {
        Arrays.sort(nominals);
        this.nominals = nominals;
    }

    public int[] changes(int value, int price) {
        int[] result = new int[10];
        int sum = price;
        int count = 0;
        while (sum != value) {
            for (int i = nominals.length - 1; i >= 0; i--) {
                int coin = nominals[i];
                while (sum + coin <= value) {
                    sum += coin;
                    result[count++] = coin;
                }
            }
        }
        return Arrays.copyOf(result, count);
    }
}
