package breakoutbasic.scenes;

import breakoutbasic.Breakout;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class is used to display the victory screen scene
 * You can also restart the game after the game has been won
 */
public class VictoryScene extends AbstractScene {
    // Text for scene
    private Text victoryText;
    private Text additionalInfo;
    private Text harderNextLevel;

    public VictoryScene() {
        // Setting screen color to black
        this.getScene().setFill(Color.BLACK);

        // Adding game over text
        addGameOverText();

        // Setup Keyboard events
        setupKeyPressedEvents();
    }

    public void setupKeyPressedEvents() {
        // IF ENTER key is pressed, change the scene to playScene
        this.getScene().setOnKeyPressed(event -> {
            int n = Breakout.getInstance().n;
            int m = Breakout.getInstance().m;
            // If all row or columns are still in the given interval, add one to each and play again
            // If not, try to add one to either row or column
            if (n <= 10 && m <= 20)
                Breakout.getInstance().setCurrentScene(new PlayScene(n += 1, m += 1));
        });
    }

    public void addGameOverText() {
        // Game over text
        this.victoryText = new Text("Victory!");
        this.victoryText.setStyle("-fx-font-size: 100px;");
        this.victoryText.setFill(Color.GREEN);
        this.getPane().getChildren().add(this.victoryText);

        // Additional info text
        this.additionalInfo = new Text("Press ENTER to play the next level!");
        this.additionalInfo.setStyle("-fx-font-size: 32px;");
        this.additionalInfo.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.additionalInfo);

        // Info about next level
        this.harderNextLevel = new Text("One row and one column will be added to the next level!");
        this.harderNextLevel.setStyle("-fx-font-size: 32px;");
        this.harderNextLevel.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.harderNextLevel);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.victoryText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.victoryText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.victoryText.setY((WindowUtils.getWindowHeight() - textHeight) / 3);
        });

        this.additionalInfo.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.additionalInfo.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.additionalInfo.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });

        this.harderNextLevel.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.harderNextLevel.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.harderNextLevel.setY((WindowUtils.getWindowHeight() - textHeight) / 1.7);
        });
    }

    public void onTick() {

    }
}
