package breakoutadvance.levels;

public class Level {
    private final String name = "level_unknown";

    private final String nextLevel = "level_unknown";

    private final double paddleWidth = 200;

    private final double ballSpeed = 1.0;

    private final double maxBallVelocity = 1.0;

    private final double powerUpSpeed = 0.01;

    private final double paddleSpeed = 1.0;

    private LevelMap levelMap = new LevelMap();

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
                ", powerUpSpeed=" + powerUpSpeed +
                ", paddleSpeed=" + paddleSpeed +
                ", levelMap=" + levelMap +
                '}';
    }
}
