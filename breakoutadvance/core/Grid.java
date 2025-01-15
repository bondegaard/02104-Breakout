package breakoutadvance.core;

import breakoutadvance.levels.LevelMap;
import breakoutadvance.objects.Block;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.WindowUtils;

/**
 * Grid is used to display blocks on top of the screen in a grid format.
 */
public class Grid {

    private final Game game; // Current game instance

    private final Block[][] grid; // 2d-Array which is filled with blocks or null depending on if the block is alive.

    private final LevelMap levelMap;

    public Grid(Game game, LevelMap levelMap) {
        this.game = game;
        this.levelMap = levelMap;
        grid = new Block[this.getCols()][this.getRows()];

        // Long side of the rectangle's length, based on window size
        double lSize = (WindowUtils.getWindowWidth() * (97.5 / 100.0) / this.getRows());

        // Short side of the rectangle's length, based on window size
        double sSize = ((WindowUtils.getWindowHeight() / 20.0));

        // Starting positions, where blank space is calculated
        double posXStart = WindowUtils.getWindowWidth() * (1.0 / 100.0);
        double posYStart = WindowUtils.getWindowWidth() * (4.0 / 100.0);

        for (int row = 0; row < this.getCols(); row++) {
            for (int col = 0; col < this.getRows(); col++) {

                if (levelMap.getLevelRows()[row].getRow().length <= col) {
                    grid[row][col] = null;
                    continue;
                }


                // Calculating positions on screen
                double posX = posXStart + col * lSize; // Horizontal position for each rectangle
                double posY = posYStart + row * sSize; // Vertical position for each rectangle


                Block.BlockType blockType = levelMap.getLevelRows()[row].getRow()[col];

                if (blockType == null) {
                    grid[row][col] = null;
                    continue;
                }
                Block block = new Block(blockType, posX, posY, lSize - Constants.OFFSET_BETWEEN_BLOCKS, sSize - Constants.OFFSET_BETWEEN_BLOCKS);

                // Adding it to grid
                grid[row][col] = block;

                // Adding it to the scene
                this.game.getPlayScene().getPane().getChildren().add(block.getNode());
            }
        }
    }


    //remove block method

    /**
     * Remove a block from the grid and scene
     *
     * @param n Row in grid
     * @param m Col in grid
     */
    public void removeBlock(int n, int m) {
        Block block = this.grid[n][m];
        if (block == null || block.getBlockType() == null) return;

        //add blockValue to score
        this.game.increaseScore(block.getBlockType().getBreakScore());

        // Get next block type or Remove from screen
        Block.BlockType nextBlockType = Block.BlockType.getNextBlockType(block.getBlockType());

        if (nextBlockType != null) {
            block.setBlockType(nextBlockType);
            block.updateImage();
        } else {
            this.game.getPlayScene().getPane().getChildren().remove(block.getNode());
            this.grid[n][m] = null;
        }
    }


    //get amount of blocks alive

    /**
     * @return Amount of alive blocks in the grid
     */
    public int getAliveAmount() {
        // If at least on block is alive, return 1
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null)
                    return 1;
            }
        }
        // If no blocks are alive, return 0
        return 0;
    }

    // Get 2d array of Block
    public Block[][] getGrid() {
        return grid;
    }


    private int getRows() {
        int maxLength = 0;
        for (int i = 0; i < this.levelMap.getLevelRows().length; i++) {
            if (this.levelMap.getLevelRows()[i].getRow().length > maxLength) {
                maxLength = this.levelMap.getLevelRows()[i].getRow().length;
            }
        }
        return maxLength;
    }

    private int getCols() {
        return this.levelMap.getLevelRows().length;
    }
}
