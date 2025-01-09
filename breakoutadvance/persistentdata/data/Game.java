package breakoutadvance.persistentdata.data;

public class Game {
    private int score = 0;

    private GameOutCome gameOutCome = GameOutCome.UNKNOWN;

    public Game(int score, GameOutCome gameOutCome) {
        this.score = score;
        this.gameOutCome = gameOutCome;
    }

    @Override
    public String toString() {
        return "Game{" +
                "score=" + score +
                '}';
    }
}
