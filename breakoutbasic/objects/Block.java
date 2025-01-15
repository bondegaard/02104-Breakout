package breakoutbasic.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends AbstractEntity {
    //Block constructor

    /**
     * Create a block
     * @param posX x-position of block
     * @param posY y-position of block
     * @param width width of block
     * @param height height of block
     * @param color color of block
     */
    public Block(double posX, double posY, double width, double height, Color color) {
        super(posX, posY, height, width); // Getting coordinates and height/width from parentclass

        // Creates new rectangle
        Rectangle rectangle = new Rectangle(posX, posY, width, height);

        // For every second row, a new color will appear, based on the colors[] array
        rectangle.setFill(color);

        this.setNode(rectangle);
    }
}
