package tictactoe.symbols;

/**
 * Common symbols
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class CommonSymbols implements Symbols {
    private final String[] symbols;

    public CommonSymbols(String[] symbols) {
        this.symbols = symbols;
    }

    @Override
    public String getLeftUpperCorner() {
        return symbols[0];
    }

    @Override
    public String getHorizontalLine() {
        return symbols[1];
    }

    @Override
    public String getUpperCross() {
        return symbols[2];
    }

    @Override
    public String getRightUpperCorner() {
        return symbols[3];
    }

    @Override
    public String getVerticalLine() {
        return symbols[4];
    }

    @Override
    public String getSpace() {
        return symbols[5];
    }

    @Override
    public String getLeftCross() {
        return symbols[6];
    }

    @Override
    public String getInnerCross() {
        return symbols[7];
    }

    @Override
    public String getRightCross() {
        return symbols[8];
    }

    @Override
    public String getLeftLowerCorner() {
        return symbols[9];
    }

    @Override
    public String getLowerCross() {
        return symbols[10];
    }

    @Override
    public String getRightLowerCorner() {
        return symbols[11];
    }
}
