package breakoutadvance.UI.menus;

import breakoutadvance.Breakout;
import breakoutadvance.UI.menus.components.UIComponentFactory;
import breakoutadvance.persistentdata.data.BallColor;
import breakoutadvance.persistentdata.data.PaddleColor;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class SettingsMenu extends BaseMenuScene {
    private List<BallColor> ballColors = Arrays.asList(BallColor.values());
    private List<PaddleColor> paddleColors = Arrays.asList(PaddleColor.values());
    private int currentBallColorIndex;
    private int currentPaddleColorIndex;
    private Text ballColorText;
    private Text paddleColorText;

    public SettingsMenu(Stage primaryStage) {
        super(primaryStage);
        VBox vbox = createVBox(Pos.CENTER, 10);
        vbox.setStyle("-fx-padding: 20;");

        Text title = UIComponentFactory.createText("Settings", 64, Color.YELLOW, currentFont);

        currentBallColorIndex = ballColors.indexOf(Breakout.getInstance().getDataManager().getData().getBallColor());
        currentPaddleColorIndex = paddleColors.indexOf(Breakout.getInstance().getDataManager().getData().getPaddleColor());

        // Volume slider
        Slider volumeSlider = UIComponentFactory.createSlider(0, 100, Breakout.getInstance().getDataManager().getData().getVolume(), 300);
        Label volumeLabel = UIComponentFactory.createLabel("Volume: " + (int) volumeSlider.getValue() + " %", 32, currentFont);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            volumeLabel.setText("Volume: " + String.format("%.0f", newValue) + "%");
            Breakout.getInstance().getDataManager().getData().setVolume(newValue.intValue());
            Breakout.getInstance().getDataManager().saveData();
        });

        VBox volumeBox = new VBox(10, volumeSlider, volumeLabel);

        // Mute checkbox
        CheckBox muteCheckBox = UIComponentFactory.createCheckBox("Mute", Breakout.getInstance().getDataManager().getData().isMute(), currentFont);
        muteCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newValue);
            Breakout.getInstance().getDataManager().saveData();
        });

        // Ball color selection
        HBox hboxBall = createColorSelector("Ball Color", ballColors, currentBallColorIndex, true);

        // Paddle color selection
        HBox hboxPaddle = createColorSelector("Paddle Color", paddleColors, currentPaddleColorIndex, false);

        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, hboxBall, hboxPaddle);
        pane.getChildren().add(vbox);
    }

    private <T> HBox createColorSelector(String label, List<T> colors, int currentIndex, boolean isBall) {
        Text leftArrow = UIComponentFactory.createText("<", 48, Color.WHITE, currentFont);
        Text colorText = UIComponentFactory.createText(colors.get(currentIndex).toString(), 48, Color.YELLOW, currentFont);
        Text rightArrow = UIComponentFactory.createText(">", 48, Color.WHITE, currentFont);

        leftArrow.setOnMouseClicked(event -> changeColor(colorText, colors, -1, isBall));
        rightArrow.setOnMouseClicked(event -> changeColor(colorText, colors, 1, isBall));

        if (isBall) {
            ballColorText = colorText;
        } else {
            paddleColorText = colorText;
        }

        return UIComponentFactory.createHBox(20, leftArrow, colorText, rightArrow);
    }

    private <T> void changeColor(Text colorText, List<T> colors, int direction, boolean isBall) {
        if (isBall) {
            currentBallColorIndex = (currentBallColorIndex + direction + colors.size()) % colors.size();
            Breakout.getInstance().getDataManager().getData().setBallColor((BallColor) colors.get(currentBallColorIndex));
        } else {
            currentPaddleColorIndex = (currentPaddleColorIndex + direction + colors.size()) % colors.size();
            Breakout.getInstance().getDataManager().getData().setPaddleColor((PaddleColor) colors.get(currentPaddleColorIndex));
        }
        colorText.setText(colors.get(isBall ? currentBallColorIndex : currentPaddleColorIndex).toString());
        Breakout.getInstance().getDataManager().saveData();
    }

    @Override
    public void onTick() {}
}
