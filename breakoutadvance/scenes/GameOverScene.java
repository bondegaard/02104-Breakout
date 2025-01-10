package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;

/**
 * This class is used to display the game is over scene
 * You can also restart the game after the game has been lost
 */
public class GameOverScene extends AbstractScene {

    // Text for scene
    private Text GameOver;
    private Text additionalInfo;

    public GameOverScene() {
        // Screen color
        this.getScene().setFill(Color.BLACK);

        // Add background
        addBackgroundImage();

        // Adding game over text
        addGameOverText();

        // Setup Keyboard events
        setupKeyPressedEvents();
    }

    public void setupKeyPressedEvents() {
        // IF ENTER key is pressed, change the scene to playScene
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Breakout.getInstance().setCurrentScene(new PlayScene(10,15));
        });
    }

    public void addGameOverText() {
        // Game over text
        this.GameOver = new Text("Game over!");
        this.GameOver.setStyle("-fx-font-size: 64px;");
        this.GameOver.setFill(Color.DARKRED);
        this.getPane().getChildren().add(this.GameOver);

        // Additional info text
        this.additionalInfo = new Text("Press ENTER to play again");
        this.additionalInfo.setStyle("-fx-font-size: 32px;");
        this.additionalInfo.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.additionalInfo);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.GameOver.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.GameOver.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.GameOver.setY((WindowUtils.getWindowHeight() - textHeight) / 2.5);
        });

        this.additionalInfo.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.additionalInfo.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.additionalInfo.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });
    }

    protected void addBackgroundImage() {
        try (FileInputStream input = new FileInputStream("assets/img/backgrounds/background2.png")) {
            Image image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );
            pane.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            System.err.println("Error loading background image, using black background instead.");
            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }

    public void onTick() {

    }
}
