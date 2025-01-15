package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * Main menu scene; inherits from AbstractMenu, which sets up the stage/scene via WindowUtils.
 */
public class MainMenu extends AbstractMenu {

    private final Text[] textItems;
    private int selectedBtn = 0;
    private Text displayHighScore;

    public MainMenu() {
        super();

        VBox vbox = createVBox(Pos.CENTER, 10);

        // Prepare menu text items
        Text startText = createMenuItem("Start", this::startGame);
        Text settingsText = createMenuItem("Settings", this::openSettings);
        Text quitText = createMenuItem("Quit", this::quitGame);

        textItems = new Text[]{startText, settingsText, quitText};

        vbox.getChildren().addAll(textItems);
        pane.getChildren().add(vbox);

        // Highlight the initially selected item
        selectText(selectedBtn);

        // Setup keyboard navigation
        setupKeyPressedEvents();

        // Add highScore
        addHighScore();
    }


    /**
     * Update the highlighting for the currently selected menu item.
     */
    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            textItems[i].setFill(i == btnIndex ? HIGHLIGHT_COLOR : NORMAL_COLOR);
        }
    }

    /**
     * Handles “Enter” key presses according to which item is currently selected.
     */
    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> startGame();
            case 1 -> openSettings();
            case 2 -> quitGame();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    /**
     * Sets up the event handler for arrow key navigation.
     */
    private void setupKeyPressedEvents() {
        // 'scene' is inherited from AbstractScene
        scene.setOnKeyPressed(event -> {
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

    public void addHighScore() {
        // Text to display HighScore
        this.displayHighScore = new Text("Top Score: " + Breakout.getInstance().getDataManager().getData().getHighscore());
        this.displayHighScore.setFont(getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf"));
        this.displayHighScore.setStyle("-fx-font-size: 80px;");
        this.displayHighScore.setFill(Color.YELLOW);
        this.displayHighScore.setStrokeWidth(3);
        this.displayHighScore.setStroke(Color.BLACK);
        this.getPane().getChildren().add(this.displayHighScore);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.displayHighScore.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater( () -> {
                double textWidth = newValue.getWidth();
                double textHeight = newValue.getHeight();
                this.displayHighScore.setX(10);
                this.displayHighScore.setY(WindowUtils.getWindowHeight() - (textHeight / 2));
            });
        });
    }

    /* Convenience methods for each action */
    private void startGame() {
        Breakout.getInstance().setCurrentScene(new PlayScene());
    }

    private void openSettings() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    private void quitGame() {
        System.exit(0);
    }
}

