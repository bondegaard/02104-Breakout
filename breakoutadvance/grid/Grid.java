package breakoutadvance.grid;

import breakoutadvance.objects.Block;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.paint.Color;

/**
 * Grid is used to display blocks on top of the screen in a grid format.
 */
public class Grid {

    private final PlayScene playScene; // Instance of current playScene

    private Block[][] grid; // 2d-Array which is filled with blocks or null depending on if the block is alive.

    private final int OFFSET = 3; // Offset between the blocks

    private final Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.YELLOW, Color.BURLYWOOD}; // Colors for rows of blocks

    /**
     * Setup a grid of blocks which is displayed on the scene.
     * @param playScene Current instance of playScene
     * @param n Amount of rows
     * @param m Amount of columns
     */
    public Grid (PlayScene playScene, int n, int m) {
        this.playScene = playScene;
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

                Block block = new Block(posX, posY, lSize - OFFSET, sSize - OFFSET, colors[(int) Math.floor(row/2)]);

                // Adding it to grid
                grid[row][col] = block;

                // Adding it to the scene
                playScene.getPane().getChildren().add(block.getNode());
            }
        }
    }


    //remove block method

    /**
     * Remove a block from the grid and scene
     * @param n Row in grid
     * @param m Col in grid
     */
    public void removeBlock(int n, int m) {
        Block block = this.grid[n][m];
        if (block == null) return;

        // Remove from screen
        playScene.getPane().getChildren().remove(block.getNode());
        this.grid[n][m] = null;
    }

    //get amount of blocks alive

    /**
     * @return Amount of alive blocks in the grid
     */
    public int getAliveAmount() {
        int aliveAmount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null)
                    aliveAmount += 1;

            }
        }
        return aliveAmount;
    }

    // Get 2d array of Block
    public Block[][] getGrid(){
        return grid;
    }
}
