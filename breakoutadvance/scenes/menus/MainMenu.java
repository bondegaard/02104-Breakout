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

    public MainMenu() {
        super();

        VBox vbox = createVBox();
        setupMenuItems(vbox);
        getPane().getChildren().add(vbox);

        setupKeyPressedEvents();
        addHighScore();
    }

    private void setupMenuItems(VBox vbox) {
        String[] labels = {"Start", "Settings", "Quit"};
        Runnable[] actions = {this::startGame, this::openSettings, this::quitGame};

        textItems = new Text[labels.length];
        for (int i = 0; i < labels.length; i++) {
            textItems[i] = createMenuItem(labels[i], actions[i]);
        }
        vbox.getChildren().addAll(textItems);
        selectText(selectedBtn);
    }

    private void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.ENTER) {
                btnEnter();
            } else {
                handleNavigation(code);
            }
        });
    }

    private void handleNavigation(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.W) {
            selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            selectedBtn = (selectedBtn + 1) % textItems.length;
        }
        selectText(selectedBtn);
    }

    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            highlightMenuItem(textItems[i], i == btnIndex);
        }
    }

    private void highlightMenuItem(Text item, boolean isSelected) {
        item.setFill(isSelected ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
    }

    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> startGame();
            case 1 -> openSettings();
            case 2 -> quitGame();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    public void addHighScore() {
        Text displayHighScore = new Text("High Score: " + Breakout.getInstance().getDataManager().getData().getHighscore());
        displayHighScore.setFont(getFont(Constants.DEFAULT_FONT));
        displayHighScore.setStyle(Constants.HIGH_SCORE_STYLE);
        displayHighScore.setFill(Constants.HIGH_SCORE_COLOR);
        displayHighScore.setStrokeWidth(Constants.HIGH_SCORE_STROKE_WIDTH);
        displayHighScore.setStroke(Constants.HIGH_SCORE_STROKE_COLOR);

        getPane().getChildren().add(displayHighScore);
        alignHighScore(displayHighScore);
    }

    private void alignHighScore(Text text) {
        text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater( () -> {
                double textHeight = newValue.getHeight();
                text.setX(10);
                text.setY(WindowUtils.getWindowHeight() - (textHeight / 2));
            });
        });
    }

    private void startGame() {
        try {
            Breakout.getInstance().setCurrentScene(new PlayScene());
        } catch (Exception e) {
            System.err.println("Failed to start the game: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openSettings() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    private void quitGame() {
        System.exit(0);
    }
}
