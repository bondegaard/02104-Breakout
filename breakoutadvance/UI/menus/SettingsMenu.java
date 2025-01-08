package breakoutadvance.UI.menus;

import breakoutadvance.scenes.AbstractScene;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        vbox.layoutXProperty().bind(this.pane.widthProperty().subtract(vbox.widthProperty()).divide(2));
        vbox.layoutYProperty().bind(this.pane.heightProperty().subtract(vbox.heightProperty()).divide(2));

        Text title = new Text("Settings");
        title.getStyleClass().add("title");

        // HBox with volume
        HBox hboxVolume = new HBox();
        Text checkBox = new Text("[x]"); // Checkbox
        Text volumeSlider = new Text("------o---"); // Slider
        hboxVolume.getChildren().addAll(checkBox, volumeSlider);

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

        this.primaryStage.setScene(scene);
    }

    @Override
    public void onTick() {

    }
}
