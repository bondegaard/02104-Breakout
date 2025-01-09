package breakoutadvance.UI.menus;

import breakoutadvance.Breakout;
import breakoutadvance.UI.menus.components.UIComponentFactory;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.SetSceneUtil;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class SettingsMenu extends AbstractMenu {

    private final String[] ballColors = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};
    private final String[] paddleColors = new String[]{"red"};

    private int currentBallColorIndex;
    private int currentPaddleColorIndex;

    // Keep references to the ImageViews so we can update them
    private ImageView ballImageView;
    private ImageView paddleImageView;

    public SettingsMenu() {
        super(); // calls AbstractMenu -> AbstractScene -> uses WindowUtils.getPrimaryStage()

        VBox vbox = createVBox(Pos.CENTER, 10);
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        Text title = UIComponentFactory.createText("Settings", 64, Color.YELLOW, currentFont);

        // Initialize color indices
        currentBallColorIndex = Arrays.asList(ballColors).indexOf(
                Breakout.getInstance().getDataManager().getData().getBallColor()
        );
        if (currentBallColorIndex < 0) currentBallColorIndex = 0;

        currentPaddleColorIndex = Arrays.asList(paddleColors).indexOf(
                Breakout.getInstance().getDataManager().getData().getPaddleColor()
        );
        if (currentPaddleColorIndex < 0) currentPaddleColorIndex = 0;

        // Volume slider
        Slider volumeSlider = UIComponentFactory.createSlider(
                0, 100,
                Breakout.getInstance().getDataManager().getData().getVolume(),
                300
        );
        Label volumeLabel = UIComponentFactory.createLabel(
                String.format("Volume: %3d %%", (int) volumeSlider.getValue()),
                20,
                currentFont
        );
        volumeLabel.setMaxWidth(400);
        volumeLabel.setMinWidth(400);

        volumeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            volumeLabel.setText(String.format("Volume: %3d %%", newVal.intValue()));
            Breakout.getInstance().getDataManager().getData().setVolume(newVal.intValue());
            Breakout.getInstance().getDataManager().saveData();
        });

        VBox volumeBox = new VBox(10, volumeSlider, volumeLabel);

        // Mute checkbox
        CheckBox muteCheckBox = UIComponentFactory.createCheckBox(
                "Mute",
                Breakout.getInstance().getDataManager().getData().isMute(),
                currentFont
        );
        muteCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newVal);
            Breakout.getInstance().getDataManager().saveData();
        });

        // Ball color selector
        HBox hboxBall = createColorSelector(ballColors, currentBallColorIndex, true);

        // Paddle color selector
        HBox hboxPaddle = createColorSelector(paddleColors, currentPaddleColorIndex, false);

        // Back button
        Text backBtn = UIComponentFactory.createText("Back", 64, Color.WHITE, currentFont);
        backBtn.setOnMouseClicked(event -> {
            new SetSceneUtil().mainMenu();
        });

        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, hboxBall, hboxPaddle, backBtn);
        pane.getChildren().add(vbox);
    }

    /**
     * Creates a color selector UI: [<] [ImageView] [>]
     */
    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        List<T> colorList = Arrays.asList(colors);

        Text leftArrow = UIComponentFactory.createText("<", 48, Color.WHITE, currentFont);
        Text rightArrow = UIComponentFactory.createText(">", 48, Color.WHITE, currentFont);

        // Initialize ImageView with the current color image
        ImageView colorImageView = createColorImageView(colorList.get(currentIndex).toString(), isBall);

        // Store references so we can update them in changeColor(...)
        if (isBall) {
            ballImageView = colorImageView;
            colorImageView.setFitWidth(Constants.BALL_RADIUS*2);
            colorImageView.setFitHeight(Constants.BALL_RADIUS*2);
        } else {
            paddleImageView = colorImageView;
            colorImageView.setFitWidth(Constants.PADDLE_WIDTH);
            colorImageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }

        leftArrow.setOnMouseClicked(event -> changeColor(colorList, -1, isBall));
        rightArrow.setOnMouseClicked(event -> changeColor(colorList, 1, isBall));

        // Create and center them in the HBox
        HBox hbox = UIComponentFactory.createHBox(20, leftArrow, colorImageView, rightArrow);
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }

    /**
     * Helper for building the ImageView from path.
     */
    private ImageView createColorImageView(String colorName, boolean isBall) {
        // If you have different paths for ball/paddle, change accordingly:
        // e.g. "assets/img/paddles/" + colorName + "_paddle.png" for paddles
        String imagePath;
        if (isBall) {
            imagePath = "./assets/img/OpenGameArt/balls/" + colorName + ".png";
        } else {
            imagePath = "./assets/img/OpenGameArt/paddles/" + colorName + ".png"; // adjust for paddle if needed
        }
        Image image = new Image(imagePath);
        return new ImageView(image);
    }

    /**
     * Changes the color in the specified direction and updates the ImageView + persistent data.
     */
    private <T> void changeColor(List<T> colors, int direction, boolean isBall) {
        if (isBall) {
            currentBallColorIndex = (currentBallColorIndex + direction + colors.size()) % colors.size();
            String newBallColor = colors.get(currentBallColorIndex).toString();

            Breakout.getInstance().getDataManager().getData().setBallColor(newBallColor);
            Breakout.getInstance().getDataManager().saveData();

            // Update the ImageView
            if (ballImageView != null) {
                ballImageView.setImage(new Image("./assets/img/OpenGameArt/balls/" + newBallColor + ".png"));
            }
        } else {
            currentPaddleColorIndex = (currentPaddleColorIndex + direction + colors.size()) % colors.size();
            String newPaddleColor = colors.get(currentPaddleColorIndex).toString();

            Breakout.getInstance().getDataManager().getData().setPaddleColor(newPaddleColor);
            Breakout.getInstance().getDataManager().saveData();

            // Update the ImageView
            if (paddleImageView != null) {
                paddleImageView.setImage(new Image("./assets/img/OpenGameArt/paddles/" + newPaddleColor + ".png"));
            }
        }
    }

    @Override
    public void onTick() {
        // Typically not needed in a static settings menu
    }
}
