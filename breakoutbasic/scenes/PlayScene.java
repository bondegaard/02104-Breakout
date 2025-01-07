package breakoutbasic.scenes;

import breakoutbasic.Breakout;
import breakoutbasic.grid.Grid;
import breakoutbasic.objects.Ball;
import breakoutbasic.objects.Block;
import breakoutbasic.objects.Paddle;
import breakoutbasic.utils.CollisionChecker;
import breakoutbasic.utils.EdgeHit;
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

    private Text infoText;

    private Random random = new Random();



    public PlayScene(int n, int m) {
        this.getScene().setFill(Color.BLACK);
        this.grid = new Grid(this, n, m);

        // Create ball and paddle
        int width = 128;
        int height = width/8;
        int radius = 16;

        this.paddle = new Paddle(this, WindowUtils.getWindowWidth()/2 - 64 , WindowUtils.getWindowHeight() * 0.8, 1.0, height, width);
        this.ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth()/2 - radius/2d  , this.paddle.getPosY()  - 2*paddle.getHeight() , .5 , -.25, radius);

        //Create solid collision area around ball
        //solidArea = new Rectangle(0, 0, (int) ball.getSize(), (int) ball.getSize());

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
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT|| event.getCode() == KeyCode.D) {
                this.paddle.setMoveRight(true);
            }
        });

        this.getScene().setOnKeyReleased( event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.KP_LEFT || event.getCode() == KeyCode.A) {
                this.paddle.setMoveLeft(false);
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.KP_RIGHT|| event.getCode() == KeyCode.D) {
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

        // Text to display controls
        this.infoText = new Text("Press 'a' to move left and 'd' to move right");
        this.infoText.setStyle("-fx-font-size: 32px;");
        this.infoText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.infoText);


        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.startOrPauseText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.startOrPauseText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.startOrPauseText.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });

        this.infoText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.infoText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.infoText.setY((WindowUtils.getWindowHeight() - textHeight) / 1.8);
        });
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void onTick() {
        if (!playing) {
            startOrPauseText.setVisible(true);
            infoText.setVisible(true);
            return;
        }

        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        if (this.ball.getPosY() >= WindowUtils.getWindowHeight()) {
            Breakout.getInstance().setCurrentScene(new GameOverScene());
            return;
        }

        if (paddle.isMoveLeft()) {
            paddle.updatePosXLeft();
        }

        if (paddle.isMoveRight()) {
            paddle.updatePosXRight();
        }

        this.ball.onTick();

        // Check Collisions
        EdgeHit ballPaddleHit = CollisionChecker.checkCollison(this.paddle, this.ball);
        if(ballPaddleHit == EdgeHit.YAXIS) {
            this.ball.setVelY(-Math.abs(this.ball.getVelY()));
        } else  if(ballPaddleHit == EdgeHit.XAXIS || ballPaddleHit == EdgeHit.BOTH) {
            this.ball.setVelY(-Math.abs(this.ball.getVelY()));
            this.ball.flipVelX();
        }

        boolean flipX = false;
        boolean flipY = false;
        for (int i = 0; i < grid.getGrid().length; i++){
            for (int j = 0; j < grid.getGrid()[i].length; j++){
                Block block = grid.getGrid()[i][j];

                if(block == null){
                    continue;
                }

                EdgeHit ballBlockHit = CollisionChecker.checkCollison(block, this.ball);

                if (ballBlockHit == EdgeHit.XAXIS){
                    flipX = true;
                    grid.removeBlock(i,j);
                } else if ( ballBlockHit == EdgeHit.YAXIS){
                    flipY = true;
                    grid.removeBlock(i,j);
                } else if (ballBlockHit == EdgeHit.BOTH){
                    flipX = true;
                    flipY = true;
                }
            }
        }
        if (flipX)ball.flipVelX();
        if (flipY)ball.flipVelY();

    }
}
