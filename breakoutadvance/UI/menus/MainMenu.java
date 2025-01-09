package breakoutadvance.UI.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Main menu scene; inherits from AbstractMenu, which sets up the stage/scene via WindowUtils.
 */
public class MainMenu extends AbstractMenu {

    private int selectedBtn = 0;
    private Text[] textItems;

    public MainMenu() {
        super();  // uses WindowUtils.getPrimaryStage() internally

        VBox vbox = createVBox(Pos.CENTER, 10);

        Text startText = createText("Start", 64, Color.WHITE);
        Text settingsText = createText("Settings", 64, Color.WHITE);
        Text quitText = createText("Quit", 64, Color.WHITE);

        textItems = new Text[] { startText, settingsText, quitText };

        vbox.getChildren().addAll(textItems);
        pane.getChildren().add(vbox);

        selectText(selectedBtn);
        setupKeyPressedEvents();
    }

    private void selectText(int btnIndex) {
        for (Text text : textItems) {
            text.setFill(Color.WHITE);
        }
        textItems[btnIndex].setFill(Color.YELLOW);
    }

    private void btnEnter() {
        switch (selectedBtn) {
            case 0:
                // Start game
                Breakout.getInstance().setCurrentScene(new PlayScene(5, 10));
                break;
            case 1:
                // Open settings
                Breakout.getInstance().setCurrentScene(new SettingsMenu());
                break;
            case 2:
                // Quit
                System.exit(0);
                break;
        }
    }

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

    @Override
    public void onTick() {
        // No periodic behavior for the main menu
    }
}