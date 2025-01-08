package breakoutadvance.UI.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.AbstractScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class SettingsMenu extends AbstractScene {
    private Pane pane;
    private Scene scene;
    private Stage primaryStage;

    public SettingsMenu(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.pane = new Pane();

        // Vertical box
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        vbox.layoutXProperty().bind(this.pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(this.pane.heightProperty().subtract(vbox.heightProperty()).divide(2));

        Text title = new Text("Settings");
        title.getStyleClass().add("title");
        title.setFont(Font.loadFont(getFont(), 64));
        title.setFill(Color.YELLOW);

        // HBox with volume
        HBox hboxVolume = new HBox();
        hboxVolume.setSpacing(10);

        // Checkbox
        CheckBox checkBox = new CheckBox();
        Label checkBoxLabel = new Label("Mute");
        checkBoxLabel.setFont(Font.loadFont(getFont(), 32));
        checkBoxLabel.setTextFill(Color.WHITE);
        checkBox.setStyle("-fx-font-size: 20px; -fx-padding: 10;");
        checkBox.setSelected(Breakout.getInstance().getDataManager().getData().isMute());
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Breakout.getInstance().getDataManager().getData().setMute(checkBox.isSelected());
                Breakout.getInstance().getDataManager().saveData();
            }
        });

        HBox hCheckBox = new HBox(10, checkBox, checkBoxLabel);

        // Volume slider
        Slider volumeSlider = new Slider(0, 100, Breakout.getInstance().getDataManager().getData().getVolume());
        volumeSlider.setPrefWidth(300);
        Label volumeSliderLabel = new Label("Volume: " + String.format("%.0f", Breakout.getInstance().getDataManager().getData().getVolume()) + " %");
        volumeSliderLabel.setFont(Font.loadFont(getFont(), 32));
        volumeSliderLabel.setTextFill(Color.WHITE);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() { // Add listener to the slider
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                volumeSliderLabel.setText("Volume: " + String.format("%.0f", newValue) + "%");
                Breakout.getInstance().getDataManager().getData().setVolume(newValue.intValue());
                Breakout.getInstance().getDataManager().saveData();
            }
        });

        VBox vVolumeBox = new VBox(10, volumeSlider, volumeSliderLabel);

        hboxVolume.getChildren().addAll(vVolumeBox, hCheckBox);

        // Ball styling
        HBox hboxBall = new HBox();
        Text left = new Text("<"); // Left arrow
        Text ballColour = new Text("Blue"); // Colours or images
        Text right = new Text(">"); // Right arrow

        hboxBall.getChildren().addAll(left, ballColour, right);

        // Paddle styling
        HBox hboxPaddle = new HBox();
        Text paddleColour = new Text("Blue"); // Colours or images
        hboxPaddle.getChildren().addAll(left, paddleColour, right);

        vbox.getChildren().addAll(title, hboxVolume, hboxBall, hboxPaddle);

        this.pane.getChildren().add(vbox);

        this.scene = new Scene(this.pane);

        this.addBackgroundImage();

        this.primaryStage.setScene(scene);

//        Breakout.getInstance().getDataManager().getData().setHighscore(7);
//        Breakout.getInstance().getDataManager().saveData();
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

    public FileInputStream getFont() {

        try {
            FileInputStream fontStream = new FileInputStream("assets/fonts/BLACEB__.ttf");
            return fontStream;
        } catch (Exception e) {
            System.out.println("Font file not found!");
        }

        return null;
    }

    @Override
    public void onTick() {

    }
}
