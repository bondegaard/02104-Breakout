package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.grid.Grid;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.Block;
import breakoutadvance.objects.Paddle;
import breakoutadvance.utils.CollisionChecker;
import breakoutadvance.utils.EdgeHit;
import breakoutadvance.utils.Sound;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
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
        this.addBackgroundImage();
        this.grid = new Grid(this, n, m);

        // Create ball and paddle
        int width = 256;
        int height = 16;
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

    public void addBackgroundImage(){
        this.getScene().setFill(Color.BLACK);
        try {
            FileInputStream input = new FileInputStream("assets/background.jpg");
            Image image = new Image(input);

            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.REPEAT,
                    BackgroundRepeat.REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);

            Background background = new Background(backgroundimage);

            this.getPane().setBackground(background);
        } catch (Exception ex) {
            System.err.println("Error loading background image");
        }
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
        // Handle unstarted or paused game
        if (!playing) {
            startOrPauseText.setVisible(true);
            infoText.setVisible(true);
            return;
        }

        // Dont display info text
        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        // Check for Victory
        if (this.grid.getAliveAmount() <= 0) {
            Breakout.getInstance().setCurrentScene(new VictoryScene());
            Sound.playSound(Sound.WON);
            return;
        }

        // Check for GameOver
        if (this.ball.getPosY() >= WindowUtils.getWindowHeight()) {
            Breakout.getInstance().setCurrentScene(new GameOverScene());
            Sound.playSound(Sound.LOSE);
            return;
        }

        // Move paddle left2
        if (paddle.isMoveLeft()) {
            paddle.updatePosXLeft();
        }

        // Move paddle right
        if (paddle.isMoveRight()) {
            paddle.updatePosXRight();
        }

        // Check Collisions for paddle and ball
        EdgeHit ballPaddleHit = CollisionChecker.checkCollision(this.paddle, this.ball);
        if(ballPaddleHit == EdgeHit.YAXIS) {
            this.ball.setPosY(this.paddle.getPosY()-this.ball.getHeight()*2);
            this.ball.setVelY(-Math.abs(this.ball.getVelY()));
            Sound.playSound(Sound.PADDLE);
        }


        // Check Collisions between ball and any blocks on the screen
        boolean flipX = false; // Should X direction be flippped
        boolean flipY = false;
        for (int i = 0; i < grid.getGrid().length; i++){
            for (int j = 0; j < grid.getGrid()[i].length; j++){
                Block block = grid.getGrid()[i][j];

                if(block == null){
                    continue;
                }

                EdgeHit ballBlockHit = CollisionChecker.checkCollision(block, this.ball);

                if (ballBlockHit == EdgeHit.XAXIS){
                    flipX = true;
                    grid.removeBlock(i,j);
                    Sound.playSound(Sound.getRandomHitSound());
                } else if ( ballBlockHit == EdgeHit.YAXIS){
                    flipY = true;
                    grid.removeBlock(i,j);
                    Sound.playSound(Sound.getRandomHitSound());
                } else if (ballBlockHit == EdgeHit.BOTH){
                    flipX = true;
                    flipY = true;
                }
            }
        }
        if (flipX)ball.flipVelX();
        if (flipY)ball.flipVelY();


        // Call the ball onTick function for it to move.
        this.ball.onTick();
    }
}
