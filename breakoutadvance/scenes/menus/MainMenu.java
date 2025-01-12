package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.utils.resources.Fonts;
import breakoutadvance.utils.SetSceneUtil;
import breakoutadvance.utils.gamephysics.BombExplosion;
import breakoutadvance.utils.resources.WindowUtils;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;

/**
 * Main menu scene; inherits from AbstractMenu, which sets up the stage/scene via WindowUtils.
 */
public class MainMenu extends AbstractMenu {

    private static final int FONT_SIZE = 64;
    private static final Color NORMAL_COLOR = Color.WHITE;
    private static final Color HIGHLIGHT_COLOR = Color.YELLOW;

    private int selectedBtn = 0;
    private final Text[] textItems;

    private Text displayHighScore;

    public MainMenu() {
        super();

        VBox vbox = createVBox(Pos.CENTER, 10);

        // Prepare menu text items
        Text startText = createMenuItem("Start", this::startGame);
        Text settingsText = createMenuItem("Settings", this::openSettings);
        Text quitText = createMenuItem("Quit", this::quitGame);

        textItems = new Text[] { startText, settingsText, quitText };

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
     * Creates a Text item for the menu with given label and on-click action.
     */
    private Text createMenuItem(String label, Runnable onClickAction) {
        Text text = createText(label, FONT_SIZE, NORMAL_COLOR);
        text.setOnMouseClicked(e -> onClickAction.run());
        return text;
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
            case 2 -> new BombExplosion(500,500, pane);
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

    /**
     * Periodic callback - not needed for the main menu.
     */
    @Override
    public void onTick() {
        // Currently not used
    }

    public void addHighScore() {
                // Text to display HighScore
        this.displayHighScore = new Text("Top Score: " + Breakout.getInstance().getDataManager().getData().getHighscore());
        this.displayHighScore.setFont(currentFont);
        this.displayHighScore.setStyle("-fx-font-size: 80px;");
        this.displayHighScore.setFill(Color.YELLOW);
        this.displayHighScore.setStrokeWidth(3);
        this.displayHighScore.setStroke(Color.BLACK);
        this.getPane().getChildren().add(this.displayHighScore);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.displayHighScore.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.displayHighScore.setX(10);
            this.displayHighScore.setY(WindowUtils.getWindowHeight() - (textHeight/2));
        });
    }

    /* Convenience methods for each action */
    private void startGame() {
        new SetSceneUtil().playScene(5, 10);
    }

    private void openSettings() {
        new SetSceneUtil().settingsMenu();
    }

    private void quitGame() {
        new SetSceneUtil().quitGame();
    }
}

