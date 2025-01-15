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

public class SettingsMenu extends AbstractMenu {
    private int currentBallColorIndex;
    private int currentPaddleColorIndex;
    private ImageView ballImageView;
    private ImageView paddleImageView;

    public SettingsMenu() {
        super();

        VBox vbox = createVBox();
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER);

        Text title = UIComponentFactory.createText("Settings", Constants.DEFAULT_FONT, Constants.DEFAULT_FONT_SIZE, Color.WHITE);

        VBox volumeBox = createVolumeBox();
        CheckBox muteCheckBox = createMuteCheckBox();
        HBox ballColorSelector = createColorSelector(Constants.BALL_COLORS, currentBallColorIndex, true);
        HBox paddleColorSelector = createColorSelector(Constants.PADDLE_COLORS, currentPaddleColorIndex, false);
        Text backBtn = createBackButton();

        vbox.getChildren().addAll(title, volumeBox, muteCheckBox, ballColorSelector, paddleColorSelector, backBtn);
        getPane().getChildren().add(vbox);

        setupKeyPressedEvents();
    }

    private VBox createVolumeBox() {
        Slider volumeSlider = UIComponentFactory.createSlider(
                0, 100,
                Breakout.getInstance().getDataManager().getData().getVolume(),
                Constants.SETTINGS_VOLUME_SLIDER_WIDTH,
                true,
                10.0,
                true
        );
        Label volumeLabel = UIComponentFactory.createLabel(
                String.format("Volume: %3d %%", (int) volumeSlider.getValue()),
                20,
                getFont(Constants.DEFAULT_FONT)
        );
        volumeLabel.setMinWidth(Constants.SETTINGS_VOLUME_LABEL_WIDTH);

        volumeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
            volumeLabel.setText(String.format("Volume: %3d %%", newVal.intValue()));
            Breakout.getInstance().getDataManager().getData().setVolume(newVal.intValue());
            Breakout.getInstance().getDataManager().saveData();
        });

        return new VBox(10, volumeSlider, volumeLabel);
    }

    private CheckBox createMuteCheckBox() {
        CheckBox muteCheckBox = UIComponentFactory.createCheckBox(
                "Mute",
                Breakout.getInstance().getDataManager().getData().isMute(),
                getFont(Constants.DEFAULT_FONT)
        );
        muteCheckBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
            Breakout.getInstance().getDataManager().getData().setMute(newVal);
            Breakout.getInstance().getDataManager().saveData();
        });
        return muteCheckBox;
    }

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
