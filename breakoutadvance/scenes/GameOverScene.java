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
    private int selectedBtn = 0;
    private final Text[] textItems;

    public GameOverScene(int score) {
        super();

        VBox vbox = createVBox();
        //vbox.setStyle("-fx-background-color: black; -fx-padding: 50");

        Text gameOverText = createMenuItem("Game Over!", null);
        gameOverText.setStyle("-fx-font-size: 128;");
        gameOverText.setFill(Color.DARKRED);
        gameOverText.setStroke(Color.BLACK);

        Text scoreText = createMenuItem("Score: " + score, null);
        scoreText.setStyle("-fx-font-size: 72;");
        scoreText.setStroke(Color.BLACK);


        Text playAgainText = createMenuItem("Play Again", playAgain());
        playAgainText.setFill(Color.YELLOW);
        playAgainText.setStroke(Color.BLACK);


        Text returnToMenuText = createMenuItem("Return to Menu", mainMenu());
        returnToMenuText.setStyle("-fx-font-size: 48;");
        returnToMenuText.setStroke(Color.BLACK);


        VBox vboxBtns = new VBox(playAgainText, returnToMenuText);
        vboxBtns.setStyle("-fx-border-color: white ;-fx-border-width: 5 ;-fx-border-style: segments(10, 15, 15, 15)  line-cap round ;");
        vboxBtns.setAlignment(Pos.TOP_CENTER);

        Text[] texts = new Text[]{gameOverText, scoreText};
        vbox.getChildren().addAll(texts);
        vbox.getChildren().add(vboxBtns);

        textItems = new Text[]{playAgainText, returnToMenuText};

        addBackgroundImage();

        // Setup Keyboard events
        setupKeyPressedEvents();

        getPane().getChildren().add(vbox);
    }

    private Runnable mainMenu() {
        return this::loadMainMenu;
    }

    private void loadMainMenu() {
        Breakout.getInstance().setCurrentScene(new MainMenu());
    }

    private Runnable playAgain() {
        return this::loadPlayScene;
    }

    private void loadPlayScene() {
        Breakout.getInstance().setCurrentScene(new PlayScene());
    }

    public void setupKeyPressedEvents() {
        // IF ENTER key is pressed, change the scene to playScene
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnEnter();
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
                selectText(selectedBtn);
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                selectedBtn = (selectedBtn + 1) % textItems.length;
                selectText(selectedBtn);
            }
        });

    }

    /**
     * Handles “Enter” key presses according to which item is currently selected.
     */
    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> loadPlayScene();
            case 1 -> loadMainMenu();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    protected void addBackgroundImage() {
        try {
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH + "background12.png");
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );
            getPane().setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            System.err.println("Error loading background image, using black background instead.");
            getPane().setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }

    /**
     * Update the highlighting for the currently selected menu item.
     */
    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            textItems[i].setFill(i == btnIndex ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
        }
    }
}
