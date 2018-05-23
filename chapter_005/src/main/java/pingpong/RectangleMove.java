package pingpong;

import javafx.scene.shape.Rectangle;

/**
 * Движение прямоугольника
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RectangleMove implements Runnable {
    private final Rectangle rect;
    private int deltaX;
    private int deltaY;
    private int limitX;
    private int limitY;


    public RectangleMove(Rectangle rect, int limitX, int limitY) {
        this.rect = rect;
        this.deltaX = 1;
        this.deltaY = 1;
        this.limitX = limitX;
        this.limitY = limitY;
    }

    @Override
    public void run() {
        while (true) {
            setCoordX();
            setCoordY();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCoordX() {
        double newX = this.rect.getX() + deltaX;
        if (newX + this.rect.getWidth() > limitX || newX < 0) {
            deltaX = -deltaX;
        }
        this.rect.setX(this.rect.getX() + deltaX);
    }

    private void setCoordY() {
        double newY = this.rect.getY() + deltaY;
        if (newY + this.rect.getHeight() > limitY || newY < 0) {
            deltaY = -deltaY;
        }
        this.rect.setY(this.rect.getY() + deltaY);
    }

}
