package breakoutadvance.UI.menus;

import breakoutadvance.scenes.AbstractScene;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;

/**
 * Base class for all menus, providing common functionality:
 *   - Font loading
 *   - Background image
 *   - Helper methods to create text and VBox
 */
public abstract class AbstractMenu extends AbstractScene {
    protected Font currentFont;

    protected Stage primaryStage;

    public AbstractMenu(Stage primaryStage) {
        super();
        this.primaryStage = primaryStage;
        loadFont();
        addBackgroundImage();
    }

    /**
     * Attempts to load a custom font from assets; falls back if unavailable.
     */
    protected void loadFont() {
        try (FileInputStream fontStream = new FileInputStream("assets/fonts/BLACEB__.ttf")) {
            currentFont = Font.loadFont(fontStream, 64);
        } catch (Exception e) {
            System.err.println("Font file not found! Falling back to Arial.");
            currentFont = Font.font("Arial", 64); // Fallback font
        }
    }

    /**
     * Sets a tiled background image; falls back to black if image fails to load.
     */
    protected void addBackgroundImage() {
        try (FileInputStream input = new FileInputStream("assets/img/cobblestoneWallWithDoor.png")) {
            Image image = new Image(input);
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );
            pane.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            System.err.println("Error loading background image, using black background.");
            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }

    /**
     * Convenience method to create a Text node in your custom font & color.
     */
    protected Text createText(String content, int size, Color color) {
        Text text = new Text(content);
        // Use the family of the currently loaded font, but override the size
        text.setFont(Font.font(currentFont.getFamily(), size));
        text.setFill(color);
        return text;
    }

    /**
     * Creates a VBox and centers it on the pane horizontally & vertically.
     */
    protected VBox createVBox(Pos alignment, int spacing) {
        VBox vbox = new VBox(spacing);
        vbox.setAlignment(alignment);

        // Bind X/Y so it remains centered even if the window resizes
        vbox.layoutXProperty().bind(pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(pane.heightProperty().subtract(vbox.heightProperty()).divide(2));

        return vbox;
    }

    /**
     * Called every frame/tick if your game loop calls it.
     * Menus can override or leave empty if not needed.
     */
    @Override
    public abstract void onTick();
}
