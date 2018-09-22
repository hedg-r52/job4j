package ru.job4j.array;

public class Check {
    public boolean mono(boolean[] data) {
        if (data.length == 0) {
            return false;
        }
        boolean result = true;
        boolean check = data[0];
        for (boolean b : data) {
            if (b != check) {
                result = false;
                break;
            }
        }
        return result;
    }
}
