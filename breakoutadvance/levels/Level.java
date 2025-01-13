package breakoutadvance.levels;

import java.util.Arrays;

public class Level {

    private String name;

    private double paddleWidth;

    private double ballSpeed;

    private LevelPowerUp [] availablePowerUps;

    private double powerUpSpeed;

    private double powerUpWidth;

    private double powerUpHeight;

    private int hearts;

    private double paddleSpeed;

    public String getName() {
        return name;
    }

    public double getPaddleWidth() {
        return paddleWidth;
    }

    public double getBallSpeed() {
        return ballSpeed;
    }

    public LevelPowerUp[] getAvailablePowerUps() {
        return availablePowerUps;
    }

    public double getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public double getPowerUpWidth() {
        return powerUpWidth;
    }

    public double getPowerUpHeight() {
        return powerUpHeight;
    }

    public int getHearts() {
        return hearts;
    }

    public double getPaddleSpeed() {
        return paddleSpeed;
    }

    @Override
    public String toString() {
        return "Level{" +
                "name='" + name + '\'' +
                ", paddleWidth=" + paddleWidth +
                ", ballSpeed=" + ballSpeed +
                ", availablePowerUps=" + Arrays.toString(availablePowerUps) +
                ", powerUpSpeed=" + powerUpSpeed +
                ", powerUpWidth=" + powerUpWidth +
                ", powerUpHeight=" + powerUpHeight +
                ", hearts=" + hearts +
                ", paddleSpeed=" + paddleSpeed +
                '}';
    }
}
