package breakoutadvance.levels;

import breakoutadvance.objects.Block;

import java.util.Arrays;

public class LevelMap {

    LevelRow[] levelRows = new LevelRow[0];

    public LevelRow[] getLevelRows() {
        return levelRows;
    }

    public void setLevelRows(LevelRow[] levelRows) {
        this.levelRows = levelRows;
    }

    @Override
    public String toString() {
        return "LevelMap{" +
                "levelRows=" + Arrays.toString(levelRows) +
                '}';
    }

    public static class LevelRow {

        Block.BlockType[] row = new Block.BlockType[0];

        public Block.BlockType[] getRow() {
            return row;
        }

        public void setRow(Block.BlockType[] row) {
            this.row = row;
        }

        @Override
        public String toString() {
            return "LevelRow{" +
                    "row=" + Arrays.toString(row) +
                    '}';
        }
    }
}
