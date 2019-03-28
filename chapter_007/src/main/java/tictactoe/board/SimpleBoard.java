package tictactoe.board;

import tictactoe.Mark;
import tictactoe.symbols.*;

/**
 * Simple board ( 3x3 and two players:cpu & user)
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 03.04.2018
 */
public class SimpleBoard implements Board {
    private static final String LN = System.getProperty("line.separator");
    private static final int DEFAULT_SIZE = 3;
    private Mark[][] values;
    private final Symbols symbols;
    private final int width;
    private final int height;

    public SimpleBoard() {
        this(new SingleSymbols(), DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public SimpleBoard(Symbols symbols) {
        this(symbols, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public SimpleBoard(int width, int height) {
        this(new SingleSymbols(), width, height);
    }

    public SimpleBoard(Symbols symbols, int width, int height) {
        this.width = width;
        this.height = height;
        this.symbols = symbols;
        this.values = new Mark[width][height];
        this.clear();
    }

    /**
     * get board width
     * @return integer value of width
     */
    @Override
    public int width() {
        return this.width;
    }

    /**
     * get board height
     * @return integer value of height
     */
    @Override
    public int height() {
        return this.height;
    }

    /**
     * draw board
     */
    public void draw() {
        System.out.println(getGridWithValues());
    }

    /**
     * clear cells
     */
    @Override
    public void clear() {
        this.values = new Mark[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.values[i][j] = Mark.NULL;
            }
        }
    }

    /**
     * Change symbol at cell
     * @param mark inserting symbol
     * @param x coordinate by x axis
     * @param y coordinate by y axis
     */
    public void changeSymbolAt(Mark mark, int x, int y) {
        this.values[x][y] = mark;
    }

    /**
     * Get symbol at cell
     * @param x coordinate by x axis
     * @param y coordinate by y axis
     * @return symbol ar coordinates
     */
    @Override
    public Mark symbolAt(int x, int y) {
        return this.values[x][y];
    }

    /**
     * Check cell at cell
     * @param x coordinate by x axis
     * @param y coordinate by x axis
     * @return true - if no mark at cell, false - if mark present
     */
    @Override
    public boolean isEmpty(int x, int y) {
        return (this.values[x][y] == Mark.NULL);
    }

    /**
     * String representation of board
     * @return string representation
     */
    public String getGridWithValues() {
        StringBuilder sb = new StringBuilder();
        String line;
        sb.append(drawUpperBorder());
        line = drawInnerWithSpaces();
        line = this.getLineWithValues(line, 0);
        sb.append(line);
        for (int h = 1; h < this.height; h++) {
            sb.append(drawInnerBorder());
            line = drawInnerWithSpaces();
            line = this.getLineWithValues(line, h);
            sb.append(line);
        }
        sb.append(drawLowerBorder());
        return sb.toString();
    }

    /**
     * Get line of board
     * @param line input line
     * @param hLevel index of line by height
     * @return line with marks
     */
    private String getLineWithValues(String line, int hLevel) {
        StringBuilder sb = new StringBuilder(line);
        for (int w = 0; w < width; w++) {
            if (this.values[w][hLevel] != Mark.NULL) {
                int pos = (
                        this.symbols.getVerticalLine().length()
                                + this.symbols.getHorizontalLine().length()
                ) * (w + 1) - this.symbols.getHorizontalLine().length() / 2;
                if (pos < line.length()) {
                    sb.setCharAt(pos - 1, this.values[w][hLevel].getMark());
                }
            }
        }
        return sb.toString();
    }

    /**
     * draw upper border
     * @return string representation of upper border
     */
    private String drawUpperBorder() {
        return drawLine(
                symbols.getLeftUpperCorner(),
                symbols.getHorizontalLine(),
                symbols.getUpperCross(),
                symbols.getRightUpperCorner()
        );
    }

    /**
     * draw inner borders with spaces
     * @return string representation of inner borders
     */
    private String drawInnerWithSpaces() {
        return drawLine(
                symbols.getVerticalLine(),
                symbols.getSpace(),
                symbols.getVerticalLine(),
                symbols.getVerticalLine()
        );
    }

    /**
     * draw inner borders with crosses
     * @return string representation of inner borders
     */
    private String drawInnerBorder() {
        return drawLine(
                symbols.getLeftCross(),
                symbols.getHorizontalLine(),
                symbols.getInnerCross(),
                symbols.getRightCross()
        );
    }

    /**
     * draw lower border
     * @return string representation of lower borders
     */
    private String drawLowerBorder() {
        return drawLine(
                symbols.getLeftLowerCorner(),
                symbols.getHorizontalLine(),
                symbols.getLowerCross(),
                symbols.getRightLowerCorner()
        );
    }

    /**
     * draw line
     * @param start first symbol of line
     * @param line line symbol
     * @param cross cross symbol
     * @param end last symbol of line
     * @return string line
     */
    private String drawLine(String start, String line, String cross, String end) {
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        for (int w = 0; w < this.width - 1; w++) {
            sb.append(line).append(cross);
        }
        sb.append(line).append(end).append(LN);
        return sb.toString();
    }
}
