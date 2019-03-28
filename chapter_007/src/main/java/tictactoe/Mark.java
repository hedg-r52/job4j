package tictactoe;

/**
 * Mark
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public enum Mark {
    X('X'),
    O('O'),
    NULL(' ');

    private char mark;

    Mark(char c) {
        this.mark = c;
    }

    public char getMark() {
        return mark;
    }
}
