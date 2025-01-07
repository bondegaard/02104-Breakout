package breakoutbasic.grid;

import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {

    private Rectangle[][] grid;

    private int offset = 3;

    private Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.BURLYWOOD};

    public Grid (PlayScene playScene, int n, int m) {
        grid = new Rectangle[n][m];

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

                // Defining the rectangle
                Rectangle rectangle = new Rectangle(posX, posY, lSize - offset, sSize - offset);

                // For every second row, a new color will appear, based on the colors[] array
                rectangle.setFill(colors[(int) Math.floor(row/2)]);
                
                // Adding it to grid
                grid[row][col] = rectangle;

                // Adding it to the scene
                playScene.getPane().getChildren().add(rectangle);
            }
        }
    }

    // If the rectangle got hit, remove it from the scene, and the grid
    public void rectangleHit(PlayScene playScene, Rectangle r) {
        if (r == null) return;

        playScene.getPane().getChildren().remove(r);
       
        // Removing the rectangle that got hit from the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == r) {
                    grid[i][j] = null; 
                    return;
                }
            }
        }
    }
}
