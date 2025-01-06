package dk.jjem.breakoutbasic.grid;

import dk.jjem.breakoutbasic.scenes.PlayScene;
import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {

    private Rectangle[][] grid;

    public Grid (PlayScene playScene, int n, int m) {
      //  playScene.getStackPane().getChildren().add(...);

        grid = new Rectangle[n][m];

        // Long side of the rectangle's length, based on window size
        double lSize = (WindowUtils.getWindowWidth() * (10.0 / 12.0) / m); // 2/12 is left for blank space

        // Short side of the rectangle's length, based on window size
        double sSize = ((WindowUtils.getWindowHeight() / 20.0) / n);

        // Starting positions, where blank space is calculated
        double posXStart = (WindowUtils.getWindowWidth() * (1.0 / 15.0));
        double posYStart = posXStart;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {

                double posX = posXStart + col * lSize; // Horizontal position for each rectangle
                double posY = posYStart + row * sSize; // Vertical position for each rectangle

                Rectangle rectangle = new Rectangle(posX, posY, lSize, sSize);

                System.out.println("posX: " + posX + " posY: " + posY + " lSize: " + lSize + " sSize: " + sSize);

                grid[row][col] = rectangle;

                rectangle.setFill(Color.BLUE);

                playScene.getStackPane().getChildren().add(rectangle);
            }
        }
    }
}
