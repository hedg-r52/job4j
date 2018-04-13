package ru.job4j.braces;

import java.util.Stack;

/**
 * Parser
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Parser {
    private String str;
    private Braces braces;
    private Stack<Character> stack;

    public Parser(String str, String strBraces) {
        this.str = str;
        this.stack = new Stack<Character>();
        this.braces = new Braces();
        this.braces.add(strBraces);
    }

    public boolean validate() {
        this.stack.clear();
        for (Character c : str.toCharArray()) {
            if (braces.list.containsKey(c)) {
                stack.push(c);
            } else if (c.equals(braces.list.get(stack.lastElement()))) {
                stack.pop();
            } else {
                break;
            }
        }
        return (stack.empty());
    }

    public String parse() {
        StringBuilder sb = new StringBuilder();
        int counter;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            counter = 0;
            if (braces.list.containsKey(c)) {
               sb.append(
                        String.format("%s:%d %s:%d\r\n",
                                c, i, this.braces.list.get(c), findCloseBrace(c, i)
                        )
                );
            }
        }
        return sb.toString();
    }

    private int findCloseBrace(char c, int iterator) {
        int result = -1;
        int counter = 1;
        for (int i = iterator + 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == c) {
                counter++;
            } else if (ch == this.braces.list.get(c)) {
                counter--;
            }
            if (counter == 0) {
                result = i;
                break;
            }
        }
        return result;

    }

    public static void main(String[] args) {
        String str = "{{[]()}()}";
        String braces = "{}[]()";
        Parser parser = new Parser(str, braces);
        if (parser.validate()) {
            System.out.println(str);
            System.out.println(parser.parse());
        }
    }
}
/*
*/