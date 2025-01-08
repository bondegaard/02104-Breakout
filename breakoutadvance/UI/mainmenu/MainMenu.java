package breakoutadvance.UI.mainmenu;

import breakoutadvance.scenes.AbstractScene;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenu extends AbstractScene {
    private Pane pane;
    private Font currentFont;

    public MainMenu(Stage primaryStage) {
        pane = new Pane();
        loadFont();

        // Create an VBox layout with center alignment
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Create text elements and add them to the HBox
        Text startText = new Text("Start");
        Text settingsText = new Text("Settings");
        Text quitText = new Text("Quit");

        Text[] textItems = {startText, settingsText, quitText};

        // Add the buttons to the VBox
        vbox.getChildren().addAll(startText, settingsText, quitText);

        // Set center alignment for the VBox
        vbox.setAlignment(Pos.CENTER);

        // Add VBox with menuBar to the root
        pane.getChildren().add(vbox);

        vbox.layoutXProperty().bind(pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(pane.heightProperty().subtract(vbox.heightProperty()).divide(2));

        selectText(startText, textItems);
        this.addBackgroundImage();

        Scene scene = new Scene(pane);

        primaryStage.setScene(scene);

        setupKeyPressedEvents();
    }

    public void addBackgroundImage(){
        this.getScene().setFill(Color.BLACK);
        try {
            FileInputStream input = new FileInputStream("assets/img/cobblestoneWallWithDoor.png");
            Image image = new Image(input);

            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);

            Background background = new Background(backgroundimage);

            pane.setBackground(background);
        } catch (Exception ex) {
            System.err.println("Error loading background image");
        }
    }

    public void loadFont() {
        try {
            FileInputStream fontStream = new FileInputStream("assets/fonts/BLACEB__.ttf");
            currentFont = Font.loadFont(fontStream, 64);
        } catch (Exception e) {
            System.out.println("Font file not found!");
        }
    }

    public void selectText(Text selectedText, Text[] textItems) {
        for (Text text : textItems) {
            text.setFont(currentFont);
            text.setFill(Color.WHITE);
        }

        selectedText.setFont(currentFont);
        selectedText.setFill(Color.YELLOW);

    }

    public void setupKeyPressedEvents() {
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Load selected
                System.out.println("You pressed Enter!");
            }
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.KP_UP || event.getCode() == KeyCode.W) {
                System.out.println("UP");
            }

            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.KP_DOWN|| event.getCode() == KeyCode.S) {
                System.out.println("DOWN");
            }
        });
    }

    @Override
    public void onTick() {

    }
}
