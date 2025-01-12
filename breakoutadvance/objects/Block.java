package breakoutadvance.objects;

import breakoutadvance.utils.gamephysics.BlockUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends AbstractEntity {
    private Image img;
    private ImageView imgView = new ImageView();

    //Block constructor

    /**
     * Create a block
     * @param posX x position
     * @param posY y position
     * @param width the width of the block
     * @param height the height of the block
     * @param color the displayed color of the block, if it fails to load the image
     * @param blockColor the color of the block displayed with image
     */
    public Block(double posX, double posY, double width, double height, Color color, String blockColor) {
        super(posX, posY, height, width); // getting coordinates and height/width from parent class

        try {
            // Get the image and add it to an ImageView
            img = BlockUtil.buildBrickImage((int) width, blockColor);
            imgView = new ImageView(img);

            // Set dimensions
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
