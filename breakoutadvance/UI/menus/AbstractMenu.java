package breakoutadvance.UI.menus;

import breakoutadvance.scenes.AbstractScene;
import breakoutadvance.utils.WindowUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;

/**
 * Base class for menu scenes, using AbstractScene's ability
 * to set the Scene on the primary stage from WindowUtils.
 */
public abstract class AbstractMenu extends AbstractScene {

    protected Font currentFont;

    /**
     * Default constructor uses the WindowUtils' primary stage.
     */
    public AbstractMenu() {
        super();  // calls AbstractScene(), which calls WindowUtils.getPrimaryStage()
        loadFont();
        addBackgroundImage();
    }

    protected void loadFont() {
        try (FileInputStream fontStream = new FileInputStream("assets/fonts/BLACEB__.ttf")) {
            currentFont = Font.loadFont(fontStream, 64);
        } catch (Exception e) {
            System.err.println("Font file not found! Fallback to Arial.");
            currentFont = Font.font("Arial", 64);
        }
    }

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
            System.err.println("Error loading background image, using black background instead.");
            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }

    protected Text createText(String content, int size, Color color) {
        Text text = new Text(content);
        text.setFont(Font.font(currentFont.getFamily(), size));
        text.setFill(color);
        return text;
    }

    protected VBox createVBox(Pos alignment, int spacing) {
        VBox vbox = new VBox(spacing);
        vbox.setAlignment(alignment);

        // Center the VBox in the pane
        vbox.layoutXProperty().bind(pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(pane.heightProperty().subtract(vbox.heightProperty()).divide(2));
        return vbox;
    }

    @Override
    public abstract void onTick();
}
