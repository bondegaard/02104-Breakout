package breakoutbasic.scenes;

import breakoutbasic.grid.Grid;
import breakoutbasic.objects.Ball;
import breakoutbasic.objects.Paddle;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.Random;

public class PlayScene extends AbstractScene {

    private boolean playing = false;

    private Grid grid;

    private Ball ball;

    private Paddle paddle;

    private Text startOrPauseText;

    private Random random = new Random();



    public PlayScene(int n, int m) {
        this.getScene().setFill(Color.BLACK);
        this.grid = new Grid(this, n, m);

        // Create ball and paddle
        this.paddle = new Paddle(this, WindowUtils.getWindowWidth()/2, WindowUtils.getWindowHeight()/20, 1.0, 8.0, 64.0);
        this.ball = new Ball(this, this.paddle.getPosX()/2, this.paddle.getPosY() + 20.0, random.nextDouble(0.5,1.0) , 1.0, 8.0);

        // Add start or pause text
        addStartOrPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();
    }

    public void setupKeyPressedEvents() {
        this.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               playing = !playing;

               if (!playing) this.startOrPauseText.setText("Press ENTER to continue");
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.paddle.setMoveLeft(true);
            } else {
                this.paddle.setMoveLeft(false);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT|| event.getCode() == KeyCode.D) {
                this.paddle.setMoveRight(true);
            } else {
                this.paddle.setMoveRight(false);
            }
        });
    }

    public void addStartOrPauseText() {
        // Text to display start or pause information
        this.startOrPauseText = new Text("Press ENTER to start");
        this.startOrPauseText.setStyle("-fx-font-size: 48px;");
        this.startOrPauseText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.startOrPauseText);

        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.startOrPauseText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.startOrPauseText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.startOrPauseText.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void onTick() {
        if (!playing) {
            startOrPauseText.setVisible(true);
            return;
        }

        startOrPauseText.setVisible(false);

        if (paddle.isMoveLeft()) {
            paddle.updatePosXLeft();
        }

        if (paddle.isMoveRight()) {
            paddle.updatePosXRight();
        }
    }
}