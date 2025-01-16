package breakoutadvance.scenes.menus;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.components.UIComponentFactory;
import breakoutadvance.utils.Constants;
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

import static breakoutadvance.utils.Fonts.getFont;
import static breakoutadvance.utils.Images.getImage;

import java.util.Arrays;
import java.util.List;

/**
 * Settings menu scene; inherits from AbstractMenu, which sets up the stage/scene via WindowUtils.
 */
public class SettingsMenu extends AbstractMenu {
    private int currentBallColorIndex; // Current chosen ball
    private int currentPaddleColorIndex; // Current chosen paddle
    private ImageView ballImageView; // Used to draw/view the ball
    private ImageView paddleImageView; // Used to draw/view the paddle

    /**
     * Constructor for settings menu, consisting of basic setup
     */
    public SettingsMenu() {
        // Parent class
        super();

        // Creating vbox and adding styles
        VBox vbox = createVBox();
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        // Creating title
        Text title = UIComponentFactory.createText("Settings", Constants.DEFAULT_FONT, Constants.DEFAULT_FONT_SIZE, Color.WHITE);

        // Creating various items, which the user can configurate
        VBox volumeBox = createVolumeBox();
        CheckBox muteCheckBox = createMuteCheckBox();
        HBox ballColorSelector = createColorSelector(Constants.BALL_COLORS, currentBallColorIndex, true);
        HBox paddleColorSelector = createColorSelector(Constants.PADDLE_COLORS, currentPaddleColorIndex, false);
        Text backBtn = createBackButton();

        // Adding it to the vbox, and adding the vbox to the pane
        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, ballColorSelector, paddleColorSelector, backBtn);
        getPane().getChildren().add(vbox);

        // Setting up key events
        setupKeyPressedEvents();
    }

    /**
     * Creating the volume vbox
     *
     * @return volume Vbox
     */
    private VBox createVolumeBox() {
        // Creating a slider to change the volume
        Slider volumeSlider = UIComponentFactory.createSlider(
                0, 100,
                Breakout.getInstance().getDataManager().getData().getVolume(),
                Constants.SETTINGS_VOLUME_SLIDER_WIDTH,
                true,
                10.0,
                true
        );
        // Creating a label for it
        Label volumeLabel = UIComponentFactory.createLabel(
                String.format("Volume: %3d %%", (int) volumeSlider.getValue()),
                20,
                getFont(Constants.DEFAULT_FONT)
        );
        volumeLabel.setMinWidth(Constants.SETTINGS_VOLUME_LABEL_WIDTH);

        // Saving user data
        volumeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            volumeLabel.setText(String.format("Volume: %3d %%", newVal.intValue()));
            Breakout.getInstance().getDataManager().getData().setVolume(newVal.intValue());
            Breakout.getInstance().getDataManager().saveData();
        });
        return new VBox(10, volumeSlider, volumeLabel);
    }

    /**
     * Creating a checkbox for muting the volume
     *
     * @return mute checkbox
     */
    private CheckBox createMuteCheckBox() {
        // Creating the checkbox
        CheckBox muteCheckBox = UIComponentFactory.createCheckBox(
                "Mute",
                Breakout.getInstance().getDataManager().getData().isMute(),
                getFont(Constants.DEFAULT_FONT)
        );
        // Saving user data
        muteCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newVal);
            Breakout.getInstance().getDataManager().saveData();
        });
        return muteCheckBox;
    }

    /**
     * Creating a back text button
     * @return
     */
    private Text createBackButton() {
        Text backBtn = UIComponentFactory.createText("Back", Constants.DEFAULT_FONT, Constants.DEFAULT_FONT_SIZE, Constants.HIGHLIGHT_TEXT_COLOR);
        backBtn.setOnMouseClicked(event -> Breakout.getInstance().setCurrentScene(new MainMenu()));
        return backBtn;
    }

    private void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                Breakout.getInstance().getDataManager().saveData();
                Breakout.getInstance().setCurrentScene(new MainMenu());
            }
        });
    }

    private void updateColorImageView(ImageView imageView, String newColor, String filePath) {
        imageView.setImage(getImage(filePath + newColor + ".png"));
        Breakout.getInstance().getDataManager().saveData();
    }

    private <T> void changeColor(List<T> colors, int direction, boolean isBall) {
        int currentIndex = isBall ? currentBallColorIndex : currentPaddleColorIndex;
        currentIndex = (currentIndex + direction + colors.size()) % colors.size();

        String newColor = colors.get(currentIndex).toString();
        if (isBall) {
            currentBallColorIndex = currentIndex;
            Breakout.getInstance().getDataManager().getData().setBallColor(newColor);
            updateColorImageView(ballImageView, newColor, Constants.BALL_FILEPATH);
        } else {
            currentPaddleColorIndex = currentIndex;
            Breakout.getInstance().getDataManager().getData().setPaddleColor(newColor);
            updateColorImageView(paddleImageView, newColor, Constants.PADDLE_FILEPATH);
        }
    }

    private Text createArrow(String content, Runnable action) {
        Text arrow = UIComponentFactory.createText(content, Constants.ARROW_FONT, Constants.ARROW_FONT_SIZE, Constants.NORMAL_TEXT_COLOR);
        arrow.setOnMouseClicked(event -> action.run());
        return arrow;
    }

    private ImageView createColorImageView(String colorName, boolean isBall) {
        String imagePath = isBall
                ? Constants.BALL_FILEPATH + colorName + ".png"
                : Constants.PADDLE_FILEPATH + colorName + ".png";

        Image image = getImage(imagePath); // Assumes Images.getImage handles loading or returns a default image in case of errors
        ImageView imageView = new ImageView(image);

        if (isBall) {
            imageView.setFitWidth(Constants.BALL_RADIUS * 2);
            imageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            imageView.setFitWidth(Constants.PADDLE_WIDTH);
            imageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }

        return imageView;
    }


    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        List<T> colorList = Arrays.asList(colors);
        ImageView colorImageView = createColorImageView(colorList.get(currentIndex).toString(), isBall);

        if (isBall) {
            ballImageView = colorImageView;
            colorImageView.setFitWidth(Constants.BALL_RADIUS * 2);
            colorImageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            paddleImageView = colorImageView;
            colorImageView.setFitWidth(Constants.PADDLE_WIDTH);
            colorImageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }

        HBox hbox = new HBox(Constants.COLOR_SELECTOR_SPACING,
                createArrow("<", () -> changeColor(colorList, -1, isBall)),
                colorImageView,
                createArrow(">", () -> changeColor(colorList, 1, isBall))
        );
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
}
