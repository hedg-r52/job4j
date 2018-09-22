package ru.job4j.array;

public class MatrixCheck {
    public boolean mono(boolean[][] data) {
        boolean result = true;
        if (!isSquareArray(data)) {
            return false;
        }
        boolean check;
        // diagonal left-up - right-down
        check = data[0][0];
        for (int i = 0; i < data.length; i++) {
            if (data[i][i] != check) {
                result = false;
                break;
            }
        }
        if (!result) {
            result = true;
            // diagonal right-up - left-down
            check = data[data.length - 1][0];
            for (int i = 0; i < data.length; i++) {
                if (data[data.length - 1 - i][i] != check) {
                    result = false;
                    break;
                }
            }
        }
        if (!result) {
            result = true;
            // vertical
            for (boolean[] row : data) {
                check = row[0];
                for (int j = 0; j < data.length; j++) {
                    if (row[j] != check) {
                        result = false;
                    }
                }
            }
        }
        if (!result) {
            result = true;
            // horizontal
            for (int j = 0; j < data.length; j++) {
                check = data[0][j];
                for (boolean[] row : data) {
                    if (row[j] != check) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    public boolean isSquareArray(boolean[][] data) {
        if (data.length < 2) {
            return false;
        }
        boolean result = true;
        for (boolean[] row : data) {
            if (data.length != row.length) {
                result = false;
                break;
            }
        }
        return result;
    }


}
