package breakoutadvance.objects;

import breakoutadvance.utils.BlockUtil;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends Entity {
    private Image img;
    private ImageView imgView = new ImageView();

    //Block constructor

    /**
     * Create a block
     * @param posX x position
     * @param posY
     * @param width
     * @param height
     * @param color
     */
    public Block(double posX, double posY, double width, double height, Color color, String blockColor) {
        super(posX, posY, height, width); // getting coordinates and height/width from parent class

        try {
            // Get the image and add it to an ImageView
            img = BlockUtil.buildBrickImage((int) width, blockColor);
            imgView = new ImageView(img);

            // Set dimentions
            imgView.setFitWidth(width);
            imgView.setFitHeight(height);

            // Set position
            imgView.setX(posX);
            imgView.setY(posY);

            this.setNode(imgView);
        } catch (Exception e) {
            //creates new rectangle
            Rectangle rectangle = new Rectangle(posX, posY, width, height);

            // For every second row, a new color will appear, based on the colors[] array
            rectangle.setFill(color);

            this.setNode(rectangle);
        }
    }
}
