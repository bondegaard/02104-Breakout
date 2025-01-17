package breakoutadvance.utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Class used to build different images into one image
 */
public class ImageBuilder {
    /**
     * Creates a single paddle image composed of:
     * - A left segment
     * - A repeated middle segment
     * - A right segment
     *
     * @param totalWidth The total desired width in pixels (including left, middle, right).
     * @param height height
     * @param constantPath constant image path
     * @param color color of image
     * @return A single combined Image of size (totalWidth × height).
     */
    public static Image buildImage(int totalWidth, int height, String constantPath, String color) {
        // Getting images
        Image leftImg   = Images.getImage(constantPath + color + "Left.png");
        Image middleImg = Images.getImage(constantPath + color + "Middle.png");
        Image rightImg  = Images.getImage(constantPath + color + "Right.png");

        // Getting dimensions of images
        assert leftImg != null; // assert makes sure that the image is not null
        int leftWidth = (int) leftImg.getWidth();
        assert rightImg != null; // assert makes sure that the image is not null
        int rightWidth = (int) rightImg.getWidth();
        int middleWidth = totalWidth - leftWidth - rightWidth;

        // If middle width is too small, an IllegalArgumentException is thrown
        if (middleWidth < 0) {
            throw new IllegalArgumentException(
                    String.format("Total width (%d) too small for left + right images (%d + %d).",
                            totalWidth, leftWidth, rightWidth)
            );
        }

        // Prepare readers for the source images
        PixelReader leftReader   = leftImg.getPixelReader();
        assert middleImg != null;
        PixelReader middleReader = middleImg.getPixelReader();
        PixelReader rightReader  = rightImg.getPixelReader();

        // Create a writable image for the final combined paddle
        WritableImage writable = new WritableImage(totalWidth, height);
        PixelWriter writer = writable.getPixelWriter();

        // 1) Copy LEFT image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < leftWidth; x++) {
                // For every pixel, it gets the ARGB value (Alpha (opacity), Red, Green, Blue)
                int argb = leftReader.getArgb(x, y);

                // Setting the ARGB value
                writer.setArgb(x, y, argb);
            }
        }

        // 2) Fill MIDDLE section by repeating the middle image's single pixel column
        for (int x = 0; x < middleWidth; x++) {
            for (int y = 0; y < height; y++) {
                // middle image is only 1 pixel wide, so always read from x=0
                int argb = middleReader.getArgb(0, y);
                writer.setArgb(leftWidth + x, y, argb);
            }
        }

        // 3) Copy RIGHT image
        int rightStartX = leftWidth + middleWidth;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < rightWidth; x++) {
                int argb = rightReader.getArgb(x, y);
                writer.setArgb(rightStartX + x, y, argb);
            }
        }

        return writable;
    }
}
