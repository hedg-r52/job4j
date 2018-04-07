package ru.job4j.braces;

/**
 * Queue
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Queue {
    public char[] brace;
    private int position = 0;

    public Queue(int size) {
        this.brace = new char[size];
    }

    public char getLast() {
        char result = 0;
        if (position != 0) {
            result = this.brace[position - 1];
        }
        return result;
    }

    public void add(char c) {
        this.brace[position++] = c;
    }

    public void del() {
        this.brace[position--] = 0;
    }

    public int length() {
        return this.position;
    }
}
