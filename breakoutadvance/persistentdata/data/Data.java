package breakoutadvance.persistentdata.data;

import java.util.Arrays;

public class Data {

    private int highscore = 0;

    private double volume = 50;

    private boolean mute = false;

    private String ballColor = "grey";

    private String paddleColor = "grey";

    private Game[] previousGames = new Game[0];

    @Override
    public String toString() {
        return "Data{" +
                "highscore=" + highscore +
                ", volume=" + volume +
                ", mute=" + mute +
                ", ballColor=" + ballColor +
                ", paddleColor=" + paddleColor +
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

    public String getBallColor() {
        return ballColor;
    }

    public void setBallColor(String ballColor) {
        this.ballColor = ballColor;
    }

    public String getPaddleColor() {
        return paddleColor;
    }

    public void setPaddleColor(String paddleColor) {
        this.paddleColor = paddleColor;
    }

    public void setPreviousGames(Game[] previousGames) {
        this.previousGames = previousGames;
    }
}
