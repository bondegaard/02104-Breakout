package breakoutadvance.persistentdata.data;

import java.util.Arrays;

public class Data {

    private int highscore = 0;

    private double volume = 0.5;

    private boolean mute = false;

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
}
