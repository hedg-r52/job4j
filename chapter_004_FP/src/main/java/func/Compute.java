package func;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Compute {
    public List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> result = new ArrayList<>();
        for (int index = start; index != end; index++) {
            result.add(func.apply((double) index));
        }
        return result;
    }

    public static void main(String[] args) {
        Compute c = new Compute();
        final int START = 1;
        final int END = 5;
        List<Double> linear = c.diapason(START, END, (x) -> (2 * x + 5));
        linear.forEach(System.out::println);
        System.out.println();
        List<Double> quadratic = c.diapason(START, END, (x) -> (Math.pow(x, 2) + 5));
        quadratic.forEach(System.out::println);
        System.out.println();
        List<Double> logarifmic = c.diapason(START, END, (x) -> (Math.log(x) + 5));
        logarifmic.forEach(System.out::println);
    }

}
