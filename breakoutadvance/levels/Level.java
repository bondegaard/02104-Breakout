package breakoutadvance.levels;

import java.util.Arrays;

public class Level {

    private String name = "level_unknown";

    private String nextLevel = "level_unknown";

    private double paddleWidth = 200;

    private double ballSpeed = 1.0;

    private double maxBallVelocity = 1.0;

    private LevelPowerUp [] availablePowerUps = new LevelPowerUp[0];

    private double powerUpSpeed = 0.01;

    private double paddleSpeed = 1.0;

    private int rows = 10;

    private int columns = 5;

    private LevelMap levelMap = new LevelMap();


    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }

    public double getPaddleWidth() {
        return paddleWidth;
    }

    public double getBallSpeed() {
        return ballSpeed;
    }

    public double getMaxBallVelocity() {
        return maxBallVelocity;
    }

    public LevelPowerUp[] getAvailablePowerUps() {
        return availablePowerUps;
    }

    public double getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public double getPaddleSpeed() {
        return paddleSpeed;
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(LevelMap levelMap) {
        this.levelMap = levelMap;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    @Override
    public String toString() {
        return "Level{" +
                "name='" + name + '\'' +
                ", paddleWidth=" + paddleWidth +
                ", ballSpeed=" + ballSpeed +
                ", maxBallVelocity=" + maxBallVelocity +
                ", availablePowerUps=" + Arrays.toString(availablePowerUps) +
                ", powerUpSpeed=" + powerUpSpeed +
                ", paddleSpeed=" + paddleSpeed +
                ", rows=" + rows +
                ", columns=" + columns +
                ", levelMap=" + levelMap +
                '}';
    }
}
