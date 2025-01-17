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
     * @return back text button
     */
    private Text createBackButton() {
        Text backBtn = UIComponentFactory.createText("Back", Constants.DEFAULT_FONT, Constants.DEFAULT_FONT_SIZE, Constants.HIGHLIGHT_TEXT_COLOR);
        backBtn.setOnMouseClicked(event -> Breakout.getInstance().setCurrentScene(new MainMenu()));
        return backBtn;
    }

    /**
     * Setting up key pressed events, making the user able to press ENTER,
     * and call a function which determines what happens
     */
    private void setupKeyPressedEvents() {
        getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                Breakout.getInstance().getDataManager().saveData();
                Breakout.getInstance().setCurrentScene(new MainMenu());
            }
        });
    }

    /**
     * Updating the image shown to the user
     *
     * @param imageView ImageView
     * @param newColor the new color
     * @param filePath the file path
     */
    private void updateColorImageView(ImageView imageView, String newColor, String filePath) {
        imageView.setImage(getImage(filePath + newColor + ".png"));
        Breakout.getInstance().getDataManager().saveData();
    }

    /**
     * Function to change color of either the ball or the paddle
     *
     * @param colors list of colors
     * @param direction direction
     * @param isBall is ball or not (paddle)
     * @param <T> function is generic, works with different variables
     */
    private <T> void changeColor(List<T> colors, int direction, boolean isBall) {
        // Getting current index, based on whether its a ball or a paddle
        int currentIndex = isBall ? currentBallColorIndex : currentPaddleColorIndex;

        // Calculating new index
        currentIndex = (currentIndex + direction + colors.size()) % colors.size();

        // Changing color of ball or paddle, based on the newly found current index
        String newColor = colors.get(currentIndex).toString();
        if (isBall) {
            currentBallColorIndex = currentIndex; // Updating current color index
            Breakout.getInstance().getDataManager().getData().setBallColor(newColor); // Setting new color in userdata
            updateColorImageView(ballImageView, newColor, Constants.BALL_FILEPATH); // Updating ball on screen
        } else {
            currentPaddleColorIndex = currentIndex; // Updating current color index
            Breakout.getInstance().getDataManager().getData().setPaddleColor(newColor); // Setting new color in userdata
            updateColorImageView(paddleImageView, newColor, Constants.PADDLE_FILEPATH); // Updating paddle on screen
        }
    }

    /**
     * Function creating a text arrow
     *
     * @param content content of text
     * @param action action
     * @return arrow Text
     */
    private Text createArrow(String content, Runnable action) {
        // Creating the text using UIComponentFactory, and assigning an action to it
        Text arrow = UIComponentFactory.createText(content, Constants.ARROW_FONT, Constants.ARROW_FONT_SIZE, Constants.NORMAL_TEXT_COLOR);
        arrow.setOnMouseClicked(event -> action.run());
        return arrow;
    }

    /**
     * Creating an ImageView for either the ball or the paddle
     *
     * @param colorName which color
     * @param isBall ball or paddle?
     * @return ImageView
     */
    private ImageView createColorImageView(String colorName, boolean isBall) {
       // Getting an image path, based on whether its the ball or paddle
        String imagePath = isBall
                ? Constants.BALL_FILEPATH + colorName + ".png"
                : Constants.PADDLE_FILEPATH + colorName + ".png";

        Image image = getImage(imagePath); // Assumes Images.getImage handles loading or returns a default image in case of errors
        ImageView imageView = new ImageView(image);

        // If it's a ball, some dimensions are set
        // If it's a paddle, different dimensions are set
        if (isBall) {
            imageView.setFitWidth(Constants.BALL_RADIUS * 2);
            imageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            imageView.setFitWidth(Constants.PADDLE_WIDTH);
            imageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }
        return imageView;
    }


    /**
     * Creating HBox for either the balls or the paddles
     * Therefore creating an option for the user to change between balls and paddles
     *
     * @param colors array of colors
     * @param currentIndex current index
     * @param isBall ball or paddle?
     * @return HBox
     * @param <T> function is generic, works with different variables
     */
    private <T> HBox createColorSelector(T[] colors, int currentIndex, boolean isBall) {
        // List of colors
        List<T> colorList = Arrays.asList(colors);

        // Creating an ImageView
        ImageView colorImageView = createColorImageView(colorList.get(currentIndex).toString(), isBall);

        // Updating ImageView for the one of them
        // If it's a ball, some dimensions are set
        // If it's a paddle, different dimensions are set
        if (isBall) {
            ballImageView = colorImageView;
            colorImageView.setFitWidth(Constants.BALL_RADIUS * 2);
            colorImageView.setFitHeight(Constants.BALL_RADIUS * 2);
        } else {
            paddleImageView = colorImageView;
            colorImageView.setFitWidth(Constants.PADDLE_WIDTH);
            colorImageView.setFitHeight(Constants.PADDLE_HEIGHT);
        }

        // New Hbox to display arrows: "<",">", to switch between the balls/paddles
        HBox hbox = new HBox(Constants.COLOR_SELECTOR_SPACING,
                createArrow("<", () -> changeColor(colorList, -1, isBall)),
                colorImageView,
                createArrow(">", () -> changeColor(colorList, 1, isBall))
        );
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }
}
