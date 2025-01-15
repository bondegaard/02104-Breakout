package breakoutadvance.scenes.menus;

import breakoutadvance.scenes.AbstractScene;
import breakoutadvance.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static breakoutadvance.utils.Fonts.getFont;
import static breakoutadvance.utils.Images.getImage;

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

        this.getScene().setFill(Color.BLACK);

        addBackgroundImage();
    }

    protected void addBackgroundImage() {
        Image image;
        try {
            image = getImage(Constants.MENU_BACKGROUND);
        } catch (Exception e) {
            System.err.println("Error loading background image, using black background instead.");
            image = null;
        }
        setPaneBackground(image);
    }

    private void setPaneBackground(Image image) {
        if (image != null) {
            BackgroundImage backgroundImage = new BackgroundImage(
                    image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT
            );
            getPane().setBackground(new Background(backgroundImage));
        } else {
            getPane().setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        }
    }


    /**
     * Creates a Text item for the menu with given label and on-click action.
     */
    protected Text createMenuItem(String label, Runnable onClickAction) {
        Text text = new Text(label);
        text.setFont(getFont(Constants.DEFAULT_FONT));
        text.setFill(Constants.NORMAL_TEXT_COLOR);
        text.setOnMouseClicked(e -> onClickAction.run());
        return text;
    }

    protected VBox createVBox() {
        VBox vbox = new VBox(Constants.DEFAULT_VBOX_SPACING);
        vbox.setAlignment(Constants.DEFAULT_VBOX_ALIGNMENT);
        centerNodeInPane(vbox);

        return vbox;
    }

    protected void centerNodeInPane(Region node) {
        node.layoutXProperty().bind(getPane().widthProperty().subtract(node.widthProperty()).divide(2));
        node.layoutYProperty().bind(getPane().heightProperty().subtract(node.heightProperty()).divide(2));
    }
}
