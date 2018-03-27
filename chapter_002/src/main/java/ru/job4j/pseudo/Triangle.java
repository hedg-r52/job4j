package ru.job4j.pseudo;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Triangle implements Shape {
    @Override
    public String draw() {
        StringBuilder pic = new StringBuilder();
        pic.append("  +  \n");
        pic.append(" + + \n");
        pic.append("+++++\n");
        return pic.toString();
    }

    public static void main(String[] args) {
        Triangle triangle = new Triangle();
        System.out.println(triangle.draw());
    }
}
