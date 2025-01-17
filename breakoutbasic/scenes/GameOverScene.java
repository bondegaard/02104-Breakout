package breakoutbasic.scenes;

import breakoutbasic.Breakout;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class is used to display the game is over scene
 * You can also restart the game after the game has been lost
 */
public class GameOverScene extends AbstractScene {

    // Text for scene
    private Text gameOver;

    /**
     * Constructor for game over scene, consisting of basic setup
     */
    public GameOverScene() {
        // Screen color
        this.getScene().setFill(Color.BLACK);

        // Adding game over text
        addGameOverText();
    }

    /**
     * Adding different text pieces
     */
    public void addGameOverText() {
        // Game over text
        this.gameOver = new Text("Game over!");
        this.gameOver.setStyle("-fx-font-size: 100px;");
        this.gameOver.setFill(Color.DARKRED);
        this.getPane().getChildren().add(this.gameOver);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.gameOver.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.gameOver.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.gameOver.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });
    }

    public void onTick() {

    }
}
