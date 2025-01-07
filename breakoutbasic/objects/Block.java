package breakoutbasic.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends Entity{
    //Block constructor

    /**
     * Create a block
     * @param posX x position
     * @param posY
     * @param width
     * @param height
     * @param color
     */
    public Block(double posX, double posY, double width, double height, Color color) {
        super(posX, posY, height, width); // getting coordinates and height/width from parentclass

        //creates new rectangle
        Rectangle rectangle = new Rectangle(posX, posY, width, height);

        // For every second row, a new color will appear, based on the colors[] array
        rectangle.setFill(color);

        this.setNode(rectangle);
    }
}
