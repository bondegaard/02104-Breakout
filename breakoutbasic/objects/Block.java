package breakoutbasic.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends Entity{
    public Block(double posX, double posY, double height, double width, Color color) {
        super(posX, posY, height, width);

        Rectangle rectangle = new Rectangle(posX, posY, width, height);

        // For every second row, a new color will appear, based on the colors[] array
        rectangle.setFill(color);

        this.setNode(rectangle);
    }
}
