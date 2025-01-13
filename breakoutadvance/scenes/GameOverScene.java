package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.menus.AbstractMenu;
import breakoutadvance.scenes.menus.MainMenu;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * This class is used to display the game is over scene
 * You can also restart the game after the game has been lost
 */
public class GameOverScene extends AbstractMenu {
    public GameOverScene() {
        super();

        VBox vbox = createVBox(Pos.CENTER, 10);
        //vbox.setStyle("-fx-background-color: black; -fx-padding: 50");

        Text gameOverText = createMenuItem("Game Over!", null);
        gameOverText.setStyle("-fx-font-size: 128;");
        gameOverText.setFill(Color.DARKRED);

        Text scoreText = createMenuItem("Score: " + Breakout.getInstance().getDataManager().getData().getPreviousGames()[Breakout.getInstance().getDataManager().getData().getPreviousGames().length-1].getScore(), null);
        scoreText.setStyle("-fx-font-size: 72;");

        Text playAgainText = createMenuItem("Play Again", playAgain());
        playAgainText.setFill(Color.YELLOW);


        Text returnToMenuText = createMenuItem("Return to Menu", mainMenu());
        returnToMenuText.setStyle("-fx-font-size: 48;");

        Text[] texts = new Text[] {gameOverText, scoreText, playAgainText, returnToMenuText};
        vbox.getChildren().addAll(texts);


        addBackgroundImage();

        // Setup Keyboard events
        setupKeyPressedEvents();

        pane.getChildren().add(vbox);
    }

    private Runnable mainMenu() {
        return () -> {Breakout.getInstance().setCurrentScene(new MainMenu());};
    }

    private Runnable playAgain() {
        return () -> {Breakout.getInstance().setCurrentScene(new PlayScene(5,10));};
    }

    public void setupKeyPressedEvents() {
        // IF ENTER key is pressed, change the scene to playScene
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) Breakout.getInstance().setCurrentScene(new PlayScene(10,15));
        });
    }

    protected void addBackgroundImage() {
        try  {
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH + "background12.png");
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
