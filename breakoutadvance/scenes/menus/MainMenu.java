package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * Main menu scene; inherits from AbstractMenu, which sets up the stage/scene via WindowUtils.
 */
public class MainMenu extends AbstractMenu {
    private Text[] textItems;
    private int selectedBtn = 0;

    /**
     * Constructor for main menu, consisting of basic setup
     */
    public MainMenu() {
        // Parent class
        super();

        // Creating menu items
        VBox vbox = createVBox();
        setupMenuItems(vbox);
        getPane().getChildren().add(vbox);
        addHighScore();

        // Setting up key events
        setupKeyPressedEvents();
    }

    /**
     * Adding items to start menu, such as displayed text "Start", "Settings" and "Quit"
     * Linking actions to labels, determining what happens when its clicked
     *
     * @param vbox what vbox to draw it in
     */
    private void setupMenuItems(VBox vbox) {
        // Creating labels and assigning functions
        String[] labels = {"Start", "Settings", "Quit"};
        Runnable[] actions = {this::startGame, this::openSettings, this::quitGame};

        // Link labels and actions together
        textItems = new Text[labels.length];
        for (int i = 0; i < labels.length; i++) {
            textItems[i] = createMenuItem(labels[i], actions[i]);
        }
        // Adding it to the vbox
        vbox.getChildren().addAll(textItems);
        selectText(selectedBtn);
    }

    /**
     * Setting up key pressed events, making the user able to press ENTER,
     * and call a function which determines what happens
     */
    private void setupKeyPressedEvents() {
        // When pressed a key
        getScene().setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            // If the key is ENTER
            if (code == KeyCode.ENTER) {
                btnEnter();
            } else {
                // If the key is not ENTER
                handleNavigation(code);
            }
        });
    }

    /**
     * Used to navigate the menu - either up or down
     * @param code keycode / key pressed
     */
    private void handleNavigation(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.W) {
            // Move one item up
            selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            // Move one item down
            selectedBtn = (selectedBtn + 1) % textItems.length;
        }
        // Highlight current text
        selectText(selectedBtn);
    }

    /**
     * Used to determine which text to highlight
     * @param btnIndex which button
     */
    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            highlightMenuItem(textItems[i], i == btnIndex);
        }
    }

    /**
     * If selected used to highlight text, displaying what item the user is on
     * If not selected, used to remove the highlight
     *
     * @param item which Text
     * @param isSelected is it selected?
     */
    private void highlightMenuItem(Text item, boolean isSelected) {
        item.setFill(isSelected ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
    }

    /**
     * Function which handles operation when ENTER key has been pressed,
     * based on what the selected button
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
     * Used to display highscore at the bottom of the screen
     */
    public void addHighScore() {
        // Styles for text
        Text displayHighScore = new Text("High Score: " + Breakout.getInstance().getDataManager().getData().getHighscore());
        displayHighScore.setFont(getFont(Constants.DEFAULT_FONT));
        displayHighScore.setStyle(Constants.HIGH_SCORE_STYLE);
        displayHighScore.setFill(Constants.HIGH_SCORE_COLOR);
        displayHighScore.setStrokeWidth(Constants.HIGH_SCORE_STROKE_WIDTH);
        displayHighScore.setStroke(Constants.HIGH_SCORE_STROKE_COLOR);

        // Adding it to the pane
        getPane().getChildren().add(displayHighScore);
        alignHighScore(displayHighScore);
    }

    /**
     * Used to determine the position of the text
     * @param text highscore text
     */
    private void alignHighScore(Text text) {
        // Used to make sure the highscore stays at the bottom of the screen, if the screen size changes
        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater( () -> {
                double textHeight = newValue.getHeight();
                text.setX(10);
                text.setY(WindowUtils.getWindowHeight() - (textHeight / 2));
            });
        });
    }

    /**
     * Starting the game
     */
    private void startGame() {
        try {
            Breakout.getInstance().setCurrentScene(new PlayScene());
        } catch (Exception e) {
            System.err.println("Failed to start the game: " + e.getMessage());
        }
    }

    /**
     * Opening settings menu
     */
    private void openSettings() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    /**
     * Quitting the game
     */
    private void quitGame() {
        System.exit(0);
    }
}
