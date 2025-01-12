package breakoutadvance.scenes.menus;

import breakoutadvance.scenes.AbstractScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Base class for menu scenes, using AbstractScene's ability
 * to set the Scene on the primary stage from WindowUtils.
 */
public abstract class AbstractMenu extends AbstractScene {
    /**
     * Default constructor uses the WindowUtils' primary stage.
     */
    public AbstractMenu() {
        super();  // calls AbstractScene(), which calls WindowUtils.getPrimaryStage()
        addBackgroundImage();
    }

    /**
     * Sets the background image to a default background image. If any errors it falls back to a completely black background.
     */
    protected void addBackgroundImage() {
        try {
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH +"background2.png");
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

    /**
     * @param content A String that will be the text that will be displayed visible on the screen.
     * @param size An integer this will be the font size.
     * @param color The text color.
     * @return
     */
    protected Text createText(String content, int size, Color color) {
        Text text = new Text(content);
        text.setFont(currentFont);
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
