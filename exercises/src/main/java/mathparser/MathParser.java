package mathparser;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import ru.job4j.utils.math.Combination;
import ru.job4j.utils.math.ExpressionParser;
import java.util.*;

/**
 * В сборниках занимательных математических задач встречается такой тип задач, когда надо из заданных чисел и
 * арифметических знаков составить выражение, дающее како-то заданное число.
 * Пример такой задачи:
 * <p>
 * Составьте из чисел 4, 1, 8, 7 и арифметических знаков выражение, равное 24
 * Ответом может служить такое выражение: (8-4)*(7-1)
 * К сожалению некоторые авторы допускают ошибки и предлагают нерешаемую задачу, поэтому их начальник обратился к
 * программистам.
 * <p>
 * Необходимо написать такую программу, которая определяет, можно ли построить из заданного набора выражение, равное
 * числу N или нет. Так как это только прототип, то достаточно написать программу, которая работает только с наборами
 * из 4-х чисел, а число N всегда равно 24.
 * <p>
 * Допустимые арифметические операторы: сложение, вычитание, умножение, деление, скобки.
 * <p>
 * На входе: массив из 4х целых чисел от 1 до 9
 * На выходе: true (если из заданного набора можно построить выражение, равное 24) или false (если нельзя)
 * <p>
 * Пример 1
 * На входе:[4, 1, 8, 7]
 * На выходе: true
 * Пояснение: (8-4)*(7-1) = 24
 * <p>
 * Пример 2
 * На входе:[1, 2, 1, 2]
 * На выходе: false
 * Пояснение: Из данного набора не возможно составить выражение равное 24
 * <p>
 * Примечание: убедитесь, что в вашем решении деление корректно работает с дробями,
 * т.е. например 4/(1 - 2/3) = 12
 * <p>
 * Представьте ваше решение в виде java-функции вида:
 * public boolean canBeEqualTo24(int[] nums) {
 * ...
 * }
 */
public class MathParser {
    private final static String OPEN_BRACKET = "(";
    private final static String CLOSE_BRACKET = ")";
    private final List<String> delimiters = Arrays.asList("+", "-", "/", "*");
    private ExpressionParser parser = new ExpressionParser();

    /**
     * Check combination of digits, operations and brackets which equals 24
     * @param nums input nums
     * @return true if exist expression which result equals 24
     */
    public boolean canBeEqualTo24(List<Integer> nums) {
        boolean result = false;
        List<String> expressions = new ArrayList<>();
        List<List<Integer>> digits = Combination.generate(nums);
        List<List<String>> operations = Combination.generateEveryWithEvery(delimiters, 3);
        for (List<Integer> list : digits) {
            String line = Joiner.on("%s").join(list);
            for (List<String> caseOfOperations : operations) {
                String lineWithParam = line;
                for (String op : caseOfOperations) {
                    lineWithParam = lineWithParam.replaceFirst("\\%s", op);
                }
                expressions.add(lineWithParam);
            }
        }
        expressions = getExpressionWithHardBracketForFourDigits(expressions);
        for (String expr : expressions) {
            if (24 == parser.calcInfix(expr)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Add all variations of brackets
     * @param expressions input expressions
     * @return list of expression with brackets
     */
    private List<String> getExpressionWithHardBracketForFourDigits(List<String> expressions) {
        List<String> result = new ArrayList<>();
        String stringOfDelimiters = Joiner.on("").join(delimiters);
        for (String expr : expressions) {
            StringTokenizer tokenizer = new StringTokenizer(expr, stringOfDelimiters, true);
            List<String> tokens = getTokens(tokenizer);
            result.add(getExpression(tokens, ImmutableMap.of()));
            result.add(getExpression(tokens, ImmutableMap.of(0, OPEN_BRACKET, 6, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(2, OPEN_BRACKET, 8, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(2, OPEN_BRACKET, 3, OPEN_BRACKET, 7, CLOSE_BRACKET, 10, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(2, OPEN_BRACKET, 5, OPEN_BRACKET, 9, CLOSE_BRACKET, 10, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(0, OPEN_BRACKET, 1, OPEN_BRACKET, 5, CLOSE_BRACKET, 8, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(0, OPEN_BRACKET, 3, OPEN_BRACKET, 7, CLOSE_BRACKET, 8, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(0, OPEN_BRACKET, 4, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(0, OPEN_BRACKET, 4, CLOSE_BRACKET, 6, OPEN_BRACKET, 10, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(2, OPEN_BRACKET, 6, CLOSE_BRACKET)));
            result.add(getExpression(tokens, ImmutableMap.of(4, OPEN_BRACKET, 8, CLOSE_BRACKET)));
        }
        return result;
    }

    /**
     * Return list of tokens from StringTokenizer
     * @param tokenizer input tokenizer
     * @return list of tokens
     */
    private List<String> getTokens(StringTokenizer tokenizer) {
        List<String> result = new ArrayList<>();
        while (tokenizer.hasMoreElements()) {
            result.add(tokenizer.nextToken());
        }
        return result;
    }

    /**
     * Build expression with brackets
     * @param tokens input tokens
     * @param map map of bracket (key - position, value - bracket
     * @return string representation of expression with brackets
     */
    private String getExpression(final List<String> tokens, Map<Integer, String> map) {
        List<String> copy = new ArrayList<>(tokens);
        for (Integer key : map.keySet()) {
            copy.add(key, map.get(key));
        }
        return Joiner.on("").join(copy);
    }

    public static void main(String[] args) {
        MathParser parser = new MathParser();
        parser.canBeEqualTo24(Arrays.asList(4, 1, 7, 8));
    }
}
