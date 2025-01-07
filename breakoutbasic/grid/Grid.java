package breakoutbasic.grid;

import breakoutbasic.objects.Block;
import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;

public class Grid {

    private Block[][] grid;

    private int offset = 3;

    private Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.BURLYWOOD};

    public Grid (PlayScene playScene, int n, int m) {
        grid = new Block[n][m];

        // Long side of the rectangle's length, based on window size
        double lSize = (WindowUtils.getWindowWidth() * (97.5/100.0) / m);

        // Short side of the rectangle's length, based on window size
        double sSize = ((WindowUtils.getWindowHeight() / 30.0));

        // Starting positions, where blank space is calculated
        double posXStart = WindowUtils.getWindowWidth() * (1.0/100.0);
        double posYStart = WindowUtils.getWindowWidth() * (3.0/100.0);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                // Calculating positions on screen
                double posX = posXStart + col * lSize; // Horizontal position for each rectangle
                double posY = posYStart + row * sSize; // Vertical position for each rectangle

                Block block = new Block(posX, posY, lSize - offset, sSize - offset, colors[(int) Math.floor(row/2)]);

                // Adding it to grid
                grid[row][col] = block;

                // Adding it to the scene
                playScene.getPane().getChildren().add(block.getNode());
            }
        }
    }
}
