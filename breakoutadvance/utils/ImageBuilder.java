package breakoutadvance.utils;

import breakoutadvance.Breakout;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageBuilder {
    public static Image buildImage(int totalWidth, int height, String constantPath, String color) {
        Image leftImg   = Images.getImage(constantPath + color + "Left.png");
        Image middleImg = Images.getImage(constantPath + color + "Middle.png");
        Image rightImg  = Images.getImage(constantPath + color + "Right.png");

        assert leftImg != null;
        int leftWidth = (int) leftImg.getWidth();
        assert rightImg != null;
        int rightWidth = (int) rightImg.getWidth();
        int middleWidth = totalWidth - leftWidth - rightWidth;

        if (middleWidth < 0) {
            throw new IllegalArgumentException(
                    String.format("Total width (%d) too small for left + right images (%d + %d).",
                            totalWidth, leftWidth, rightWidth)
            );
        }


        PixelReader leftReader   = leftImg.getPixelReader();
        assert middleImg != null;
        PixelReader middleReader = middleImg.getPixelReader();
        PixelReader rightReader  = rightImg.getPixelReader();

        WritableImage writable = new WritableImage(totalWidth, height);
        PixelWriter writer = writable.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < leftWidth; x++) {
                int argb = leftReader.getArgb(x, y);
                writer.setArgb(x, y, argb);
            }
        }

        for (int x = 0; x < middleWidth; x++) {
            for (int y = 0; y < height; y++) {
                // middle image is only 1 pixel wide, so always read from x=0
                int argb = middleReader.getArgb(0, y);
                writer.setArgb(leftWidth + x, y, argb);
            }
        }

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
