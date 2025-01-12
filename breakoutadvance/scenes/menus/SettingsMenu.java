package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.components.UIComponentFactory;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.SetSceneUtil;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class SettingsMenu extends AbstractMenu {

    private final String[] ballColors = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};
    private final String[] paddleColors = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};

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

        Text title = UIComponentFactory.createText("Settings", "BLACEB__.TTF",64, Color.YELLOW);

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
                300,
                true,
                10.0,
                true
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
        Text backBtn = UIComponentFactory.createText("Back", "BLACEB__.TTF",64, Color.WHITE);
        backBtn.setOnMouseClicked(event -> {
            new SetSceneUtil().mainMenu();
        });

        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, hboxBall, hboxPaddle, backBtn);
        pane.getChildren().add(vbox);

        setupKeyPressedEvents();
    }

    /**
     * Creates a color selector UI: [<] [ImageView] [>]
     */
    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        List<T> colorList = Arrays.asList(colors);

        Text leftArrow = UIComponentFactory.createText("<", "BLACEB__.TTF", 48, Color.WHITE);
        Text rightArrow = UIComponentFactory.createText(">", "BLACEB__.TTF", 48, Color.WHITE);

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
            imagePath = Constants.BALL_FILEPATH + colorName + ".png";
        } else {
            imagePath = Constants.PADDLE_FILEPATH + colorName + ".png"; // adjust for paddle if needed
        }
        Image image = Images.getImage(imagePath);
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
                ballImageView.setImage(Images.getImage(Constants.BALL_FILEPATH + newBallColor + ".png"));
            }
        } else {
            currentPaddleColorIndex = (currentPaddleColorIndex + direction + colors.size()) % colors.size();
            String newPaddleColor = colors.get(currentPaddleColorIndex).toString();

            Breakout.getInstance().getDataManager().getData().setPaddleColor(newPaddleColor);
            Breakout.getInstance().getDataManager().saveData();

            // Update the ImageView
            if (paddleImageView != null) {
                paddleImageView.setImage(Images.getImage( Constants.PADDLE_FILEPATH + newPaddleColor + ".png"));
            }
        }
    }

    /**
     * Sets up the event handler for arrow key navigation.
     */
    private void setupKeyPressedEvents() {
        // 'scene' is inherited from AbstractScene
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Breakout.getInstance().getDataManager().saveData();
                new SetSceneUtil().mainMenu();
            }
        });
    }

    @Override
    public void onTick() {
        // Typically not needed in a static settings menu
    }
}
