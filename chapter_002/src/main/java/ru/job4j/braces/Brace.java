package ru.job4j.braces;

/**
 * Brace
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Brace {
    public final char openBrace;
    public final char closeBrace;

    public Brace(char openBrace, char closeBrace) {
        this.openBrace = openBrace;
        this.closeBrace = closeBrace;
    }
}
