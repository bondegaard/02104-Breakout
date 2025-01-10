package breakoutadvance.utils;

import breakoutadvance.Breakout;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class PaddleUtil {
    /**
     * Creates a single paddle image composed of:
     *   - A left segment (fixed width, e.g. 16)
     *   - A repeated middle segment (1 pixel wide repeated N times)
     *   - A right segment (fixed width, e.g. 16)
     *
     * @param totalWidth  The total desired width in pixels (including left, middle, right).
     * @return A single combined Image of size (totalWidth × height).
     *
     * Requirements/Assumptions:
     *   - totalWidth must be >= (leftWidth + rightWidth).
     */
    public static Image buildPaddleImage(int totalWidth) {
        String color = Breakout.getInstance().getDataManager().getData().getPaddleColor();
        Image leftImg   = AssetManager.getInstance().getImage("OpenGameArt-paddles-" + color + "Left");
        Image middleImg = AssetManager.getInstance().getImage("OpenGameArt-paddles-" + color + "Middle");
        Image rightImg  = AssetManager.getInstance().getImage("OpenGameArt-paddles-" + color + "Right");

        int leftWidth = (int) leftImg.getWidth();
        int rightWidth = (int) rightImg.getWidth();
        int middleWidth = totalWidth - leftWidth - rightWidth;

        if (middleWidth < 0) {
            throw new IllegalArgumentException(
                    String.format("Total width (%d) too small for left + right images (%d + %d).",
                            totalWidth, leftWidth, rightWidth)
            );
        }


        // Prepare readers for the source images
        PixelReader leftReader   = leftImg.getPixelReader();
        PixelReader middleReader = middleImg.getPixelReader();
        PixelReader rightReader  = rightImg.getPixelReader();

        // Create a writable image for the final combined paddle
        WritableImage writable = new WritableImage(totalWidth, Constants.PADDLE_HEIGHT);
        PixelWriter writer = writable.getPixelWriter();

        // 1) Copy LEFT image
        for (int y = 0; y < Constants.PADDLE_HEIGHT; y++) {
            for (int x = 0; x < leftWidth; x++) {
                int argb = leftReader.getArgb(x, y);
                writer.setArgb(x, y, argb);
            }
        }

        // 2) Fill MIDDLE section by repeating the middle image's single pixel column
        int middleStartX = leftWidth;
        for (int x = 0; x < middleWidth; x++) {
            for (int y = 0; y < Constants.PADDLE_HEIGHT; y++) {
                // middle image is only 1 pixel wide, so always read from x=0
                int argb = middleReader.getArgb(0, y);
                writer.setArgb(middleStartX + x, y, argb);
            }
        }

        // 3) Copy RIGHT image
        int rightStartX = leftWidth + middleWidth;
        for (int y = 0; y < Constants.PADDLE_HEIGHT; y++) {
            for (int x = 0; x < rightWidth; x++) {
                int argb = rightReader.getArgb(x, y);
                writer.setArgb(rightStartX + x, y, argb);
            }
        }

        return writable;
    }
}
