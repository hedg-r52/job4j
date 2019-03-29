package barbell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 *
 *  В спортивном зале города N появилась штанга и набор блинов разного веса. Для занятия спортом необходимо
 *   распределить блины по двум концам штанги таким образом, чтобы суммарный вес по оба конца штанги был одинаковый.
 *   Рассчитайте максимальный вес, который смогут поднимать спортсмены при заданном наборе блинов ( с допущением, что
 *   гриф штанги невесомый)
 *
 *   Решение должно быть представлено в виде java-программы, у которой:
 *   На входе: массив целых чисел, обозначающих веса блинов, входящих в набор
 *   На выходе: целое число, обозначающее максимальный вес, который можно поднять с заданным набором блинов
 *
 *   Примеры:
 *   1.
 *   На входе: [1, 2, 3, 6]
 *   На выходе: 12
 *   Пояснение: можно нацепить блины {1, 2, 3} с одной стороны и блин {6} с другой, что в сумме даст 12.
 *
 *   2.
 *   На входе: [1, 2, 3, 4, 5, 6]
 *   На выходе: 20
 *   Пояснение: можно нацепить блины {2, 3, 5} и блины {4, 6} с другой, что в сумме даст 20.
 *
 *   2.
 *   На входе: [1, 2]
 *   На выходе: 0
 *   Пояснение: данный набор блинов невозможно нацепить без потери равновесия.
 *
 *   Ограничения:
 *   Блин может весить от 0 до 20 фунтов. В наборе может присутствовать от 1 до 1000 блинов.
 *   Суммарный вес целого набора не может превышать 10 000 фунтов.
 *   В примере: [3, 4, 3, 3, 2]
 *   Ожидаемое значение: 12
 *   Полученное значение: 0
 */
public class Barbell {
    private int limit;

    /**
     * Constructor
     * @param limit upper bound
     */
    public Barbell(int limit) {
        this.limit = limit;
    }

    /**
     * Getting max weights on to sides
     * @param weights input weights
     * @return maximum weights on to sides of barbell
     */
    public int maxWeight(List<Integer> weights) {
        int result = 0;
        List<Integer> leftIndexes = new ArrayList<>();
        List<Integer> rightIndexes;
        if (weights.size() > 1) {
            int sum = weights.stream().mapToInt(x -> x).sum();
            int maxHalfBarbellWeight = Math.min(limit / 2, sum / 2);
            weights.sort(Comparator.reverseOrder());
            while (maxHalfBarbellWeight > 0) {
                leftIndexes = indexesOfValue(weights, leftIndexes, maxHalfBarbellWeight);
                rightIndexes = indexesOfValue(weights, leftIndexes, maxHalfBarbellWeight);
                if (sum(leftIndexes, weights) == sum(rightIndexes, weights)) {
                    result = sum(leftIndexes, weights) * 2;
                    break;
                }
                maxHalfBarbellWeight--;
            }
        }
        return result;
    }

    /**
     * Getting list of indexes which summary equals $value
     * @param input input weights
     * @param excludedIndexes indexes of weights which must be excluded
     * @param value value for check sum of weights
     * @return list of indexes
     */
    private List<Integer> indexesOfValue(List<Integer> input, List<Integer> excludedIndexes, int value) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (excludedIndexes.contains(i)) {
                continue;
            }
            int calculate = input.get(i) + sum(result, input);
            if (calculate > value) {
                continue;
            } else {
                result.add(i);
                if (calculate == value) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Sum by indexes
     * @param indexes list of indexes which need to add
     * @param weights input weights
     * @return sum
     */
    private int sum(List<Integer> indexes, List<Integer> weights) {
        int result = 0;
        for (Integer index : indexes) {
            result += weights.get(index);
        }
        return result;
    }
}
