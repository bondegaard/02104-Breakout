package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.components.UIComponentFactory;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
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

import static breakoutadvance.utils.Fonts.getFont;

public class SettingsMenu extends AbstractMenu {
    private int currentBallColorIndex;
    private int currentPaddleColorIndex;

    // Keep references to the ImageViews so we can update them
    private ImageView ballImageView;
    private ImageView paddleImageView;

    public SettingsMenu() {
        super(); // calls AbstractMenu -> AbstractScene -> uses WindowUtils.getPrimaryStage()

        VBox vbox = createVBox();
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        Text title = UIComponentFactory.createText("Settings",  Constants.DEFAULT_FONT, 64, Color.WHITE);

        // Initialize color indices
        currentBallColorIndex = Arrays.asList(Constants.BALL_COLORS).indexOf(
                Breakout.getInstance().getDataManager().getData().getBallColor()
        );
        if (currentBallColorIndex < 0) currentBallColorIndex = 0;

        currentPaddleColorIndex = Arrays.asList(Constants.PADDLE_COLORS).indexOf(
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
                getFont( Constants.DEFAULT_FONT)
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
                getFont(Constants.DEFAULT_FONT)
        );
        muteCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newVal);
            Breakout.getInstance().getDataManager().saveData();
        });

        // Ball color selector
        HBox hboxBall = createColorSelector(Constants.BALL_COLORS, currentBallColorIndex, true);

        // Paddle color selector
        HBox hboxPaddle = createColorSelector(Constants.PADDLE_COLORS, currentPaddleColorIndex, false);

        // Back button
        Text backBtn = UIComponentFactory.createText("Back", Constants.DEFAULT_FONT, Constants.DEFAULT_FONT_SIZE, Constants.HIGHLIGHT_TEXT_COLOR);
        backBtn.setOnMouseClicked(event -> {
            Breakout.getInstance().setCurrentScene(new MainMenu());
        });

        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, hboxBall, hboxPaddle, backBtn);
        getPane().getChildren().add(vbox);

        setupKeyPressedEvents();
    }

    /**
     * Creates a color selector UI: [<] [ImageView] [>]
     */
    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        List<T> colorList = Arrays.asList(colors);

        Text leftArrow = UIComponentFactory.createText("<", Constants.FONT_FILEPATH + "BLACEB__.ttf", Constants.DEFAULT_FONT_SIZE*3/4, Constants.NORMAL_TEXT_COLOR);
        Text rightArrow = UIComponentFactory.createText(">", Constants.FONT_FILEPATH + "BLACEB__.ttf", Constants.DEFAULT_FONT_SIZE*3/4, Constants.NORMAL_TEXT_COLOR);

        // Initialize ImageView with the current color image
        ImageView colorImageView = createColorImageView(colorList.get(currentIndex).toString(), isBall);

        // Store references so we can update them in changeColor(...)
        if (isBall) {
            ballImageView = colorImageView;
            colorImageView.setFitWidth(Constants.BALL_RADIUS * 2);
            colorImageView.setFitHeight(Constants.BALL_RADIUS * 2);
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
        String imagePath;
        if (isBall) {
            imagePath = Constants.BALL_FILEPATH + colorName + ".png";
        } else {
            imagePath = Constants.PADDLE_FILEPATH + colorName + ".png";
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
                paddleImageView.setImage(Images.getImage(Constants.PADDLE_FILEPATH + newPaddleColor + ".png"));
            }
        }
    }

    /**
     * Sets up the event handler for arrow key navigation.
     */
    private void setupKeyPressedEvents() {
        // 'scene' is inherited from AbstractScene
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                Breakout.getInstance().getDataManager().saveData();
                Breakout.getInstance().setCurrentScene(new MainMenu());
            }
        });
    }
}
