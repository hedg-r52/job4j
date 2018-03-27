package ru.job4j.pseudo;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Square implements Shape {

    @Override
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("++++\n");
        pic.append("+  +\n");
        pic.append("+  +\n");
        pic.append("++++\n");
        return pic.toString();
    }

    public static void main(String[] args) {
        Square square = new Square();
        System.out.println(square.draw());
    }
}
