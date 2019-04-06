package ru.job4j.utils.math;

import com.google.common.base.Joiner;
import java.util.*;
import java.util.function.BiFunction;

/**
 * @author Andrei Solovev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ExpressionParser {
    private final static String ADD = "+";
    private final static String SUB = "-";
    private final static String DIV = "/";
    private final static String MUL = "*";
    private final static String OPEN_BRACKET = "(";
    private final static String CLOSE_BRACKET = ")";
    private static String operators = String.format("%s%s%s%s", ADD, SUB, DIV, MUL);
    private static String delimiters = String.format("%s%s%s", OPEN_BRACKET, CLOSE_BRACKET, operators);
    private static final Map<String, Integer> PRIORITY;

    private Map<String, BiFunction<String, String, String>> operations = new HashMap<>();

    static {
        PRIORITY = new HashMap<>();
        PRIORITY.put(OPEN_BRACKET, 1);
        PRIORITY.put(ADD, 2);
        PRIORITY.put(SUB, 2);
        PRIORITY.put(MUL, 3);
        PRIORITY.put(DIV, 3);
    }

    /**
     * Constructor
     */
    public ExpressionParser() {
        operations.put(ADD, this::handleAdd);
        operations.put(SUB, this::handleSub);
        operations.put(DIV, this::handleDiv);
        operations.put(MUL, this::handleMul);
    }

    /**
     * Convert infix expression to postfix (Reverse Polish notation)
     * @param infix input infix expression
     * @return output postfix (Reverse Polish notation) expression
     */
    public String infix2postfix(String infix) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String current;
        while (tokenizer.hasMoreTokens()) {
            current = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(current)) {
                System.out.println("Incorrect expression.");
                return "";
            }
            if (current.equals(" ")) {
                continue;
            }
            if (isDelimiter(current)) {
                if (current.equals(OPEN_BRACKET)) {
                    stack.push(current);
                } else if (current.equals(CLOSE_BRACKET)) {
                    while (!stack.peek().equals(OPEN_BRACKET)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Brackets aren't consistent");
                            return "";
                        }
                    }
                    stack.pop();
//                    if (!stack.isEmpty()) {
//                        postfix.add(stack.pop());
//                    }
                } else {
                    while (!stack.isEmpty() && (priority(current) <= priority(stack.peek()))) {
                        postfix.add(stack.pop());
                    }
                    stack.push(current);
                }
            } else {
                postfix.add(current);
            }
        }
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) {
                postfix.add(stack.pop());
            } else {
                System.out.println("Brackets aren't consistent");
                return "";
            }
        }
        return Joiner.on(" ").join(postfix);
    }

    /**
     * Calculate Reverse Polish notation expression
     * @param postfix input Reverse Polish notation
     * @return result
     */
    public Double calcPostfix(String postfix) {
        StringTokenizer tokenizer = new StringTokenizer(postfix, " ", false);
        Deque<String> stack = new ArrayDeque<>();
        while (tokenizer.hasMoreTokens()) {
            String x = tokenizer.nextToken();
            if (operators.contains(x)) {
                String right = stack.pop();
                String left = stack.pop();
                if (right.equals("0") && x.equals(DIV)) {
                    return 0.0;
                }
                stack.push(operation(left, right, x));
            } else {
                stack.push(x);
            }
        }
        return string2double(stack.pop());
    }

    /**
     * Calculate infix expression
     * @param infix input infix expression
     * @return result
     */
    public Double calcInfix(String infix) {
        return calcPostfix(infix2postfix(infix));
    }

    /**
     * Execute operation by operation string
     * @param left left
     * @param right right
     * @param operation operation
     * @return string representation
     */
    public String operation(String left, String right, String operation) {
        return operations.get(operation).apply(left, right);
    }

    /**
     * Shrink fraction
     * @param expression input expression
     * @return shrunk expression
     */
    public String shrinkFraction(String expression) {
        String[] parts = expression.split(DIV, 2);
        int left = Integer.valueOf(parts[0]);
        int right = Integer.valueOf(parts[1]);
        for (int i = left; i > 1; i--) {
            if (right % i == 0 && left % i == 0) {
                right /= i;
                left /= i;
            }
        }
        return (right == 1) ? String.valueOf(left) : String.format("%s%s%s", left, DIV, right);
    }

    /**
     * Expand fraction
     * @param expression input expression
     * @param divider multiplier for both parts of fraction
     * @return new expanded fraction
     */
    public String expandToFraction(String expression, int divider) {
        String result = (expression.contains("/") ? expression : String.format("%s%s%s", expression, DIV, 1));
        String[] parts = result.split(DIV, 2);
        int left = Integer.valueOf(parts[0]);
        int right = Integer.valueOf(parts[1]);
        return String.format("%s%s%s", left * divider, DIV, right * divider);
    }

    /**
     * Least common multiple
     * @param first first
     * @param second second
     * @return value of least common multiple
     */
    public int leastCommonMultiple(int first, int second) {
        int max = Math.max(first, second);
        boolean isFinded = false;
        int result;
        int factor = 1;
        do {
            result = max * factor;
            if (result % first == 0 && result % second == 0) {
                isFinded = true;
            } else {
                factor++;
            }
        } while (!isFinded);
        return result;
    }

    /**
     * handle subtraction
     * @param left left
     * @param right right
     * @return result
     */
    private String handleSub(String left, String right) {
        int leftDivider = getDivider(left);
        int rightDivider = getDivider(right);
        int nok = this.leastCommonMultiple(leftDivider, rightDivider);
        int leftPartDividend = getPartDividend(left, leftDivider, nok);
        int rightPartDividend = getPartDividend(right, rightDivider, nok);
        String result = String.format(
                "%s%s%s",
                leftPartDividend - rightPartDividend,
                DIV,
                nok
        );
        return shrinkFraction(result);
    }

    /**
     * get divider
     * @param input value
     * @return fraction
     */
    private int getDivider(String input) {
        return !input.contains(DIV) ? 1 : Integer.valueOf(input.substring(input.indexOf(DIV) + 1));
    }

    /**
     * handle subtraction
     * @param left left
     * @param right right
     * @return result
     */
    private String handleAdd(String left, String right) {
        int leftDivider = (getDivider(left));
        int rightDivider = (getDivider(right));
        int nok = this.leastCommonMultiple(leftDivider, rightDivider);
        int leftPartDividend = getPartDividend(left, leftDivider, nok);
        int rightPartDividend = getPartDividend(right, rightDivider, nok);
        String result = String.format(
                "%s%s%s",
                leftPartDividend + rightPartDividend,
                DIV,
                nok
        );
        return shrinkFraction(result);
    }

    /**
     * Get dividend
     * @param expr expression
     * @param divider divider
     * @param nok least common multiple
     * @return result
     */
    private int getPartDividend(String expr, int divider, int nok) {
        String r = expandToFraction(expr, nok / divider);
        String[] rparts = r.split(DIV, 2);
        return Integer.valueOf(rparts[0]);
    }

    /**
     * handle divide
     * @param left left
     * @param right right
     * @return result
     */
    private String handleDiv(final String left, final String right) {
        String l = expandToFraction(left, 1);
        String r = expandToFraction(right, 1);
        String[] lparts = l.split(DIV, 2);
        String[] rparts = r.split(DIV, 2);
        String result = String.format(
                "%s%s%s",
                Integer.valueOf(lparts[0]) * Integer.valueOf(rparts[1]),
                DIV,
                Integer.valueOf(lparts[1]) * Integer.valueOf(rparts[0])
        );
        return shrinkFraction(result);
    }

    /**
     * Handle for multiply
     * @param left multiplicant
     * @param right factor
     * @return result of multiply
     */
    private String handleMul(String left, String right) {
        String l = expandToFraction(left, 1);
        String r = expandToFraction(right, 1);
        String[] lparts = l.split(DIV, 2);
        String[] rparts = r.split(DIV, 2);
        String result = String.format(
                "%s%s%s",
                Integer.valueOf(lparts[0]) * Integer.valueOf(rparts[0]),
                DIV,
                Integer.valueOf(lparts[1]) * Integer.valueOf(rparts[1])
        );
        return shrinkFraction(result);
    }

    /**
     * Check: is token a delimiter?
     * @param token input
     * @return true - if delimiter, false - if not
     */
    private boolean isDelimiter(String token) {
        boolean result = false;
        if (token.length() == 1) {
            result = delimiters.contains(token);
        }
        return result;
    }

    /**
     * Check: is token a operator?
     * @param token input
     * @return true - if operator, false - if not
     */
    private boolean isOperator(String token) {
        return operators.contains(token);
    }

    /**
     * Get priority
     * @param token input token
     * @return priority
     */
    private int priority(String token) {
        return PRIORITY.get(token);
    }

    /**
     * Convert fraction or value to double value
     * @param expression input expression
     * @return double result
     */
    private Double string2double(String expression) {
        Double result;
        if (!expression.contains(DIV)) {
            result = Double.valueOf(expression);
        } else {
            String[] parts = expression.split(DIV, 2);
            result = Double.valueOf(parts[0]) / Double.valueOf(parts[1]);
        }
        return result;
    }
}
