package breakoutadvance.objects;

import breakoutadvance.utils.BlockUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block extends AbstractEntity {

    private BlockType blockType;
    private Image img;
    private ImageView imgView = new ImageView();

    //Block constructor

    /**
     * Create a block
     *
     * @param blockType type of block
     * @param posX      x position
     * @param posY      y position
     * @param width     the width of the block
     * @param height    the height of the block
     */
    public Block(BlockType blockType, double posX, double posY, double width, double height) {
        super(posX, posY, height, width); // getting coordinates and height/width from parent class
        this.blockType = blockType;

        try {
            // Get the image and add it to an ImageView
            img = BlockUtil.buildBrickImage((int) width, blockType.getImage());
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
            rectangle.setFill(blockType.getColor());

            this.setNode(rectangle);
        }
    }

    public void updateImage() {
        img = BlockUtil.buildBrickImage((int) width, blockType.getImage());

        if (this.getNode() instanceof ImageView imageView) {
            imageView.setImage(img);
        }
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public enum BlockType {
        BLUE(2, 300, "blue", Color.BLUE),
        GREEN(1, 300, "green", Color.GREEN),
        PINK(3, 500, "pink", Color.PINK),
        RED(4, 700, "red", Color.RED),
        YELLOW(0, 100, "yellow", Color.YELLOW),
        ;

        private final int id;

        private final int breakScore;

        private final String image;

        private final Color color;

        BlockType(int id, int breakScore, String image, Color color) {
            this.id = id;
            this.breakScore = breakScore;
            this.image = image;
            this.color = color;
        }

        public static BlockType getBlockType(int id) {
            for (BlockType blockType : BlockType.values()) {
                if (blockType.getId() == id) {
                    return blockType;
                }
            }
            return null;
        }

        public static BlockType getNextBlockType(BlockType blockType) {
            return getBlockType(blockType.getId() - 1);
        }

        public int getId() {
            return id;
        }

        public int getBreakScore() {
            return breakScore;
        }

        public String getImage() {
            return image;
        }

        public Color getColor() {
            return color;
        }
    }
}
