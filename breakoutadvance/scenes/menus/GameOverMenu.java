package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;

/**
 * This class is used to display the game over scene
 * You can also restart the game after the game has been lost.
 */
public class GameOverMenu extends AbstractMenu {
    private int selectedBtn = 0;
    private final Text[] textItems;

    public GameOverMenu(int score) {
        super();

        // Create menu items
        Text gameOverText = createStyledText("Game Over!", Constants.GAME_OVER_TEXT_STYLE, Constants.GAME_OVER_TEXT_COLOR, Color.BLACK);
        Text scoreText = createStyledText("Score: " + score, Constants.SCORE_TEXT_STYLE, Constants.NORMAL_TEXT_COLOR, Color.BLACK);
        Text playAgainText = createMenuItem("Play Again", this::loadPlayScene);
        Text returnToMenuText = createMenuItem("Return to Menu", this::loadMainMenu);

        // Create layout
        VBox vboxTexts = new VBox(Constants.DEFAULT_VBOX_SPACING, gameOverText, scoreText);
        vboxTexts.setAlignment(Constants.DEFAULT_VBOX_ALIGNMENT);
        VBox vboxButtons = createMenuVBox(playAgainText, returnToMenuText);
        VBox mainVBox = new VBox(vboxTexts, vboxButtons);
        mainVBox.setAlignment(Pos.CENTER);
        mainVBox.setSpacing(Constants.DEFAULT_VBOX_SPACING);

        centerNodeInPane(mainVBox);
        getPane().getChildren().add(mainVBox);

        textItems = new Text[]{playAgainText, returnToMenuText};

        // Highlight first button
        selectText(selectedBtn);

        // Setup events
        setupKeyPressedEvents();
        addBackgroundImage();
    }

    private void selectText(int btnIndex) {
        for (int i = 0; i < textItems.length; i++) {
            highlightMenuItem(textItems[i], i == btnIndex);
        }
    }

    private void highlightMenuItem(Text item, boolean isSelected) {
        item.setFill(isSelected ? Constants.HIGHLIGHT_TEXT_COLOR : Constants.NORMAL_TEXT_COLOR);
    }

    private Text createStyledText(String content, String style, Color fill, Color stroke) {
        Text text = new Text(content);
        text.setFont(getFont(Constants.DEFAULT_FONT));
        text.setStyle(style);
        text.setFill(fill);
        text.setStroke(stroke);
        return text;
    }

    private VBox createMenuVBox(Text... texts) {
        VBox vbox = new VBox(Constants.DEFAULT_VBOX_SPACING, texts);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle(Constants.BUTTON_BOX_STYLE);
        return vbox;
    }

    private void navigateMenu(KeyCode code) {
        if (code == KeyCode.UP || code == KeyCode.W) {
            selectedBtn = (selectedBtn - 1 + textItems.length) % textItems.length;
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            selectedBtn = (selectedBtn + 1) % textItems.length;
        }
        selectText(selectedBtn);
    }

    public void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnEnter();
            } else {
                navigateMenu(event.getCode());
            }
        });
    }

    private void btnEnter() {
        switch (selectedBtn) {
            case 0 -> loadPlayScene();
            case 1 -> loadMainMenu();
            default -> throw new IllegalStateException("Unexpected button index: " + selectedBtn);
        }
    }

    private void loadMainMenu() {
        Breakout.getInstance().setCurrentScene(new MainMenu());
    }

    private void loadPlayScene() {
        Breakout.getInstance().setCurrentScene(new PlayScene());
    }
}
