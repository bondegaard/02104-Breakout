package breakoutadvance.UI.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Main Menu screen for Breakout.
 */
public class MainMenu extends AbstractMenu {

    private int selectedBtn = 0;
    private Text[] textItems;

    public MainMenu(Stage primaryStage) {
        super(primaryStage);

        // Create a centered VBox to hold the menu items
        VBox vbox = createVBox(Pos.CENTER, 10);

        // Create text items
        Text startText = createText("Start", 64, Color.WHITE);
        Text settingsText = createText("Settings", 64, Color.WHITE);
        Text quitText = createText("Quit", 64, Color.WHITE);

        // Put them in an array for easy highlighting
        textItems = new Text[]{startText, settingsText, quitText};

        // Add them to the layout, then add the layout to the pane
        vbox.getChildren().addAll(textItems);
        pane.getChildren().add(vbox);

        // Highlight the first option
        selectText(selectedBtn);

        // Handle arrow/enter keys
        setupKeyPressedEvents();
    }

    /**
     * Changes the color of the currently selected menu item.
     */
    private void selectText(int btnIndex) {
        for (Text text : textItems) {
            text.setFill(Color.WHITE);
        }
        textItems[btnIndex].setFill(Color.YELLOW);
    }

    /**
     * Called when ENTER is pressed on the selected menu item.
     */
    private void btnEnter() {
        switch (selectedBtn) {
            case 0:
                // Start game
                Breakout.getInstance().setCurrentScene(new PlayScene(5, 10));
                break;
            case 1:
                // Open settings
                Breakout.getInstance().setCurrentScene(new SettingsMenu(primaryStage));
                break;
            case 2:
                // Quit application
                System.exit(0);
                break;
            default:
                System.out.println("Unknown selection: " + selectedBtn);
        }
    }

    /**
     * Set up input for navigating the menu with arrow/WASD keys and Enter.
     */
    private void setupKeyPressedEvents() {
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
        // If you don’t need periodic updates in the menu, leave it empty
    }
}
