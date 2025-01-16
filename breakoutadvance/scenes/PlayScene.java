package breakoutadvance.scenes;

import breakoutadvance.core.Game;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.LifesDisplay;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Fonts;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

public class PlayScene extends AbstractScene {

    private final LifesDisplay lifesDisplay;
    private final breakoutadvance.core.Game game;
    private Text startOrPauseText;
    private Text infoText;
    private Text addInfoText;
    private Text deathPauseText;
    private Text deathInfoText;
    private Text displayScoreText;


    public PlayScene() {
        this.game = new breakoutadvance.core.Game(this);

        this.addBackgroundImage();

        // Add start or pause text
        addStartOrPauseText();

        // Add death note text
        addDeathPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();

        // Add lives
        this.lifesDisplay = new LifesDisplay();
        this.lifesDisplay.updateLives(this, this.game.getLives());

        // Add score
        addDisplayScore();
    }

    public void setupKeyPressedEvents() {
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                this.game.togglePlaying();
                addInfoText.setVisible(false);

                if (!this.game.isPlaying()) this.startOrPauseText.setText("Press ENTER/SPACE to continue");
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.game.getPaddle().setMoveLeft(true);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                this.game.getPaddle().setMoveRight(true);
            }
        });

        this.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.game.getPaddle().setMoveLeft(false);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT || event.getCode() == KeyCode.D) {
                this.game.getPaddle().setMoveRight(false);
            }
        });
    }

    public void addBackgroundImage() {
        this.getScene().setFill(Color.BLACK);
        try {
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH + "background12.png");

            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);

            Background background = new Background(backgroundimage);

            this.getPane().setBackground(background);
        } catch (Exception ex) {
            System.err.println("Error loading background image");
        }
    }


    public void addStartOrPauseText() {
        this.startOrPauseText = new Text("Press ENTER to start");
        this.startOrPauseText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.startOrPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.startOrPauseText.setFill(Color.WHITE);
        this.startOrPauseText.setStroke(Color.BLACK);
        this.startOrPauseText.setStrokeWidth(0.75);
        this.getPane().getChildren().add(this.startOrPauseText);

        this.infoText = new Text("Press 'a' to move left and 'd' to move right");
        this.infoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.infoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.infoText.setFill(Color.WHITE);
        this.infoText.setStroke(Color.BLACK);
        this.infoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.infoText);

        this.addInfoText = new Text("When started, you can press ENTER to pause the game");
        this.addInfoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.addInfoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.addInfoText.setFill(Color.WHITE);
        this.addInfoText.setStroke(Color.BLACK);
        this.addInfoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.addInfoText);

        // Manually set the positions initially
        addCenterTextListener(this.startOrPauseText, 2, 2);
        addCenterTextListener(this.infoText, 2, 1.8);
        addCenterTextListener(this.addInfoText, 2, 1.6);
    }

    public void addDeathPauseText() {
        // Text to display start or pause information
        this.deathPauseText = new Text("You Died. You have " + this.game.getLives() + " left.");
        this.deathPauseText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.deathPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.deathPauseText.setFill(Color.WHITE);
        this.deathPauseText.setStroke(Color.BLACK);
        this.deathPauseText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.deathPauseText);
        this.deathPauseText.setVisible(false);

        // Text to display controls
        this.deathInfoText = new Text("Press Enter to start again");
        this.deathInfoText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.deathInfoText.setStyle("-fx-font-size: 32px;-fx-font-weight: bold;");
        this.deathInfoText.setFill(Color.WHITE);
        this.deathInfoText.setStroke(Color.BLACK);
        this.deathInfoText.setStrokeWidth(0.5);
        this.getPane().getChildren().add(this.deathInfoText);
        this.deathInfoText.setVisible(false);


        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.deathPauseText, 2, 2);
        addCenterTextListener(this.deathInfoText, 2, 1.8);
    }

    public void addDisplayScore() {
        // Text to display start or pause information
        this.displayScoreText = new Text("Score: " + this.game.getScore());
        this.displayScoreText.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf", 80));
        this.displayScoreText.setFill(Color.WHITE);
        this.displayScoreText.setStroke(Color.BLACK);
        this.displayScoreText.setStrokeWidth(1.5);
        this.displayScoreText.setVisible(true);
        this.getPane().getChildren().add(this.displayScoreText);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.displayScoreText, 20, 1);
    }

    /**
     * Makes sure the text is at the middle of the screen
     *
     * @param text
     * @param xOffset
     * @param yOffSet
     */
    private void addCenterTextListener(Text text, double xOffset, double yOffSet) {
        if (text == null) return;

        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {

            // Make sure bounds of text are updated correctly before calculating new position
            Platform.runLater(() -> {
                double textWidth = newValue.getWidth();
                double textHeight = newValue.getHeight();

                text.setX((WindowUtils.getWindowWidth() - textWidth) / xOffset);
                text.setY((WindowUtils.getWindowHeight() - textHeight) / yOffSet);
            });
        });
    }


    /**
     * Every tick, this function will run
     */
    public void onTick() {
        // Handle text on screen
        if (handleTextChangeAndVisibility()) return;

        // Check for Victory and return if victory has been reached
        if (this.game.checkVictory()) return;


        // Check if each ball is out of bounds
        this.game.checkBallOutOfBounds();

        // Move paddle left
        if (this.game.getPaddle().isMoveLeft()) {
            this.game.getPaddle().updatePosXLeft();
        }

        // Move paddle right
        if (this.game.getPaddle().isMoveRight()) {
            this.game.getPaddle().updatePosXRight();
        }

        // Check Collisions for paddle and ball
        this.game.checkBallPaddleCollision();

        // Handle ontick for each ball
        this.game.getBalls().forEach(Ball::onTick);


        // Remove powerups under screen or collides with paddle
        this.game.checkPowerupCollision();

        // handle ontick for each powerup
        this.game.getPowerups().forEach(p -> p.onTick(this.game.getLevel().getPowerUpSpeed()));
    }

    /**
     * Handles when text should be displayed on the screen.
     * It also handles which text should be displayed!
     *
     * @return if text should be displayed and stop game from running when displayed
     */
    private boolean handleTextChangeAndVisibility() {
        if (!this.game.isPlaying()) {
            if (this.game.isDied()) {
                startOrPauseText.setVisible(false);
                infoText.setVisible(false);
                deathPauseText.setVisible(true);
                deathInfoText.setVisible(true);
            } else {
                deathInfoText.setVisible(false);
                deathPauseText.setVisible(false);
                startOrPauseText.setVisible(true);
                infoText.setVisible(true);
            }
            return true;
        }

        // Dont display info text
        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        this.game.setDied(false);
        deathPauseText.setVisible(false);
        deathInfoText.setVisible(false);
        return false;
    }

    public Text getDisplayScoreText() {
        return displayScoreText;
    }

    public LifesDisplay getLifesDisplay() {
        return lifesDisplay;
    }

    public Text getDeathPauseText() {
        return deathPauseText;
    }

    public Game getGame() {
        return game;
    }
}
