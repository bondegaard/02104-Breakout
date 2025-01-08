package breakoutadvance.UI.mainmenu;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.AbstractScene;

import breakoutadvance.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class MainMenu extends AbstractScene {
    private Pane pane;
    private Font currentFont;
    private Scene scene;

    private int selectedBtn = 0;

    private Text[] textItems;

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

        textItems = new Text[]{startText, settingsText, quitText};

        // Add the buttons to the VBox
        vbox.getChildren().addAll(startText, settingsText, quitText);

        // Set center alignment for the VBox
        vbox.setAlignment(Pos.CENTER);

        // Add VBox with menuBar to the root
        pane.getChildren().add(vbox);

        vbox.layoutXProperty().bind(pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(pane.heightProperty().subtract(vbox.heightProperty()).divide(2));

        selectText(selectedBtn, textItems);
        this.addBackgroundImage();

        scene = new Scene(pane);

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

    public void selectText(int btnIndex, Text[] textItems) {
        Text selectedText = textItems[btnIndex];

        for (Text text : textItems) {
            text.setFont(currentFont);
            text.setFill(Color.WHITE);
        }

        selectedText.setFont(currentFont);
        selectedText.setFill(Color.YELLOW);

    }

    public void btnEnter() {
        switch (selectedBtn) {
            case 0:
                Breakout.getInstance().setCurrentScene(new PlayScene(5, 10));
                break;
            case 1:
                System.out.println("Settings is yet to be implemented");
                break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }
    }

    public void setupKeyPressedEvents() {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Load selected
                System.out.println("You pressed Enter!");
                btnEnter();
            }
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.KP_UP || event.getCode() == KeyCode.W) {
                System.out.println("UP");
                selectedBtn--;

                if (selectedBtn < 0) {
                    selectedBtn = textItems.length - 1;
                }

                selectText(selectedBtn, textItems);
            }

            if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.KP_DOWN|| event.getCode() == KeyCode.S) {
                System.out.println("DOWN");
                selectedBtn++;

                selectedBtn = selectedBtn % textItems.length;

                selectText(selectedBtn, textItems);
            }
        });
    }

    @Override
    public void onTick() {

    }
}
