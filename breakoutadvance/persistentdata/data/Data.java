package breakoutadvance.persistentdata.data;

import java.util.Arrays;
import javafx.scene.paint.Color;

public class Data {

    private int highscore = 0;

    private double volume = 50;

    private boolean mute = false;

    private BallColor ballColor = BallColor.GREY;

    private PaddleColor paddleColor = PaddleColor.GREY;

    private Game[] previousGames = new Game[0];


    @Override
    public String toString() {
        return "Data{" +
                "highscore=" + highscore +
                ", volume=" + volume +
                ", mute=" + mute +
                ", previousGames=" + Arrays.toString(previousGames) +
                '}';
    }

    public int getHighscore() {
        return highscore;
    }

    public double getVolume() {
        return volume;
    }

    public boolean isMute() {
        return mute;
    }

    public Game[] getPreviousGames() {
        return previousGames;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public BallColor getBallColor() {
        return ballColor;
    }

    public void setBallColor(BallColor ballColor) {
        this.ballColor = ballColor;
    }

    public PaddleColor getPaddleColor() {
        return paddleColor;
    }

    public void setPaddleColor(PaddleColor paddleColor) {
        this.paddleColor = paddleColor;
    }

    public void setPreviousGames(Game[] previousGames) {
        this.previousGames = previousGames;
    }
}
