package breakoutadvance.UI.menus;

import breakoutadvance.scenes.AbstractScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public abstract class BaseMenuScene extends AbstractScene {
    protected Pane pane;
    protected Scene scene;
    protected Stage primaryStage;
    protected Font currentFont;

    public BaseMenuScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.pane = new Pane();
        this.scene = new Scene(pane);
        this.primaryStage.setScene(scene);
        loadFont();
        addBackgroundImage();
    }

    protected void loadFont() {
        try (FileInputStream fontStream = new FileInputStream("assets/fonts/BLACEB__.ttf")) {
            currentFont = Font.loadFont(fontStream, 64);
        } catch (Exception e) {
            System.out.println("Font file not found!");
            currentFont = Font.font("Arial", 64); // Fallback font
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
            System.err.println("Error loading background image");
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
        vbox.layoutXProperty().bind(pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(pane.heightProperty().subtract(vbox.heightProperty()).divide(2));
        return vbox;
    }

    @Override
    public abstract void onTick();
}
