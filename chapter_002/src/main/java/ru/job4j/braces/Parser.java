package ru.job4j.braces;

import java.sql.SQLOutput;

/**
 * Parser
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Parser {
    private Brace[] braces;
    private String str;
    private Queue queue;
    private int countBraces = 0;

    public Parser(String str) {
        this.braces = new Brace[5];
        this.str = str;
        this.queue = new Queue(str.length());
    }

    public void add(Brace brace) {
        this.braces[countBraces++] = brace;
    }

    public boolean validate() {
        Queue queue = new Queue(str.length());
        for(char c : str.toCharArray()) {
            if (isOpenBrace(c)) {
                queue.add(c);
            } else if (isCloseBrace(c)) {
                if (getOpenBrace(c) == queue.getLast()) {
                    queue.del();
                } else {
                    queue.add(c);
                }
            }
        }
        return (queue.length() == 0);
    }

    public String parse() {
        StringBuilder sb = new StringBuilder();
        Queue queue = new Queue(str.length());
        int counter;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            counter = 0;
            if (isOpenBrace(c)) {
                counter++;
                sb.append(c + ":" + i);
                for (int j = i + 1; j < str.length(); j++) {
                    char ch = str.charAt(j);
                    if (c == ch) {
                        counter++;
                    } else if (getCloseBrace(c) == ch) {
                        counter--;
                    }
                    if (counter == 0) {
                        sb.append(" " + getCloseBrace(c) + ":" + j);
                        sb.append(System.lineSeparator());
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }

    private boolean isOpenBrace(char c) {
        boolean result = false;
        for (int i = 0; i < countBraces; i++) {
            if (c == this.braces[i].openBrace) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean isCloseBrace(char c) {
        boolean result = false;
        for (int i = 0; i < countBraces; i++) {
            if (c == this.braces[i].closeBrace) {
                result = true;
                break;
            }
        }
        return result;
    }

    private char getOpenBrace(char closeBrace) {
        char result = 0;
        for (int i = 0; i < countBraces; i++) {
            if (closeBrace == this.braces[i].closeBrace) {
                result = this.braces[i].openBrace;
                break;
            }
        }
        return result;
    }

    private char getCloseBrace(char openBrace) {
        char result = 0;
        for (int i = 0; i < countBraces; i++) {
            if (openBrace == this.braces[i].openBrace) {
                result = this.braces[i].closeBrace;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String str = "{{[]()}()}";
        Parser parser = new Parser(str);
        parser.add(new Brace('(', ')'));
        parser.add(new Brace('{', '}'));
        parser.add(new Brace('[', ']'));
        if (parser.validate()) {
            System.out.println(str);
            System.out.println(parser.parse());
        }
    }
}
