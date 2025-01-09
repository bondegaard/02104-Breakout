package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.grid.Grid;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.Block;
import breakoutadvance.objects.Paddle;
import breakoutadvance.persistentdata.data.Data;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PlayScene extends AbstractScene {

    private boolean playing = false;

    private Grid grid;

    private List<Ball> balls = new ArrayList<>();

    private Paddle paddle;

    private Text startOrPauseText;

    private Text infoText;

    private Text addInfoText;

    private Random random = new Random();

    private final double maxAddedVel = 1.0;

    private int lives = 3;
    private Text deathPauseText;
    private Text deathInfoText;
    private boolean died = false;

    public int score = 100;


    public PlayScene(int n, int m) {
        this.addBackgroundImage();
        this.grid = new Grid(this, 8, 10);

        // Create ball and paddle
        int paddleWidth = 256;
        int height = 16;
        int radius = 16;
        
        this.paddle = new Paddle(this, WindowUtils.getWindowWidth()/2 - ((double) paddleWidth /2), WindowUtils.getWindowHeight() * 0.8, 1.0, height, paddleWidth);

        // Calculating angle/velocity
        double[] vel = calculateStartVelForBall();
        Ball ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth()/2 - (int) (radius/2), this.paddle.getPosY() - 4*paddle.getHeight(), vel[0] , vel[1], radius);
        balls.add(ball);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth()/2 - (int) (radius/2), this.paddle.getPosY() - 4*paddle.getHeight());


        // Add start or pause text
        addStartOrPauseText();

        // Add death note text
        addDeathPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();

    }

    public double[] calculateStartVelForBall() {
        // Creating a random angle to start from
        // Interval for velX is 0.2 to 0.75, and it varies from a negative and a positive number
        // velY is calculated based on (maxAddedVel - velX)
        boolean positiveNumber = random.nextBoolean();
        double velX = random.nextDouble(0.2,0.75);
        double velY = maxAddedVel - velX;
        velX = (positiveNumber) ? velX : -velX;

        return new double[]{velX,-velY};
    }

    public void addBackgroundImage(){
        this.getScene().setFill(Color.BLACK);
        try {
            FileInputStream input = new FileInputStream("assets/img/background.jpg");
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
               addInfoText.setVisible(false);

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

        // Text to inform user that you can pause the game
        this.addInfoText = new Text("When started, you can press ENTER to pause the game");
        this.addInfoText.setStyle("-fx-font-size: 32px;");
        this.addInfoText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.addInfoText);

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

        this.addInfoText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.addInfoText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.addInfoText.setY((WindowUtils.getWindowHeight() - textHeight) / 1.6);
        });

    }

    public void addDeathPauseText() {
        // Text to display start or pause information
        this.deathPauseText = new Text("You Died. You have " + lives + " left.");
        this.deathPauseText.setStyle("-fx-font-size: 48px;");
        this.deathPauseText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.deathPauseText);
        this.deathPauseText.setVisible(false);

        // Text to display controls
        this.deathInfoText = new Text("Press Enter to start again");
        this.deathInfoText.setStyle("-fx-font-size: 32px;");
        this.deathInfoText.setFill(Color.WHITE);
        this.getPane().getChildren().add(this.deathInfoText);
        this.deathInfoText.setVisible(false);


        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        this.deathPauseText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.deathPauseText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.deathPauseText.setY((WindowUtils.getWindowHeight() - textHeight) / 2);
        });

        this.deathInfoText.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();
            this.deathInfoText.setX((WindowUtils.getWindowWidth() - textWidth) / 2);
            this.deathInfoText.setY((WindowUtils.getWindowHeight() - textHeight) / 1.8);
        });
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    public void onTick() {
        // Handle unstarted or paused game
        if (!playing) {
            if (died) {
                deathPauseText.setVisible(true);
                deathInfoText.setVisible(true);
            }
            else {
                startOrPauseText.setVisible(true);
                infoText.setVisible(true);
            }
            return;
        }

        // Dont display info text
        startOrPauseText.setVisible(false);
        infoText.setVisible(false);

        died = false;
        deathPauseText.setVisible(false);
        deathInfoText.setVisible(false);

        // Check for Victory
        if (this.grid.getAliveAmount() <= 0) {
            Breakout.getInstance().setCurrentScene(new VictoryScene());
            Sound.playSound(Sound.WON);

            // Save new highscore
            Data data = Breakout.getInstance().getDataManager().getData();
            if (data.getHighscore() < this.score)
                data.setHighscore(this.score);
            Breakout.getInstance().getDataManager().saveData();
            return;
        }

        // Check if eah ball is out of bounds
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();
            if (ball.getPosY() >= WindowUtils.getWindowHeight()) {
                iterator.remove();
                this.getPane().getChildren().remove(ball.getNode());

                // Check for lives
                if (balls.isEmpty()) {
                    lives--;
                    this.deathPauseText.setText("You Died. You have " + lives + " lives left.");
                    died = true;
                    playing = !playing;
                    resetBallAndPaddle();
                    if (lives <= 0) {
                      Breakout.getInstance().setCurrentScene(new GameOverScene());
                      Sound.playSound(Sound.LOSE);
                    }
                }
                return;
            }
        }

        // Move paddle left
        if (paddle.isMoveLeft()) {
            paddle.updatePosXLeft();
        }

        // Move paddle right
        if (paddle.isMoveRight()) {
            paddle.updatePosXRight();
        }

        // Check Collisions for paddle and ball
        for (Ball ball : balls) {
            EdgeHit ballPaddleHit = CollisionChecker.checkCollision(this.paddle, ball);
            if (ballPaddleHit == EdgeHit.YAXIS) {
                double[] vel = calculateNewXVelocityAfterPaddleHit(ball);

                ball.setPosY(this.paddle.getPosY() - ball.getHeight() * 2); // Making sure the ball only hits the paddle once
                //ball.setVelY(-Math.abs(ball.getVelY()));
                ball.setVelX(vel[0]);
                ball.setVelY(vel[1]);

                Sound.playSound(Sound.PADDLE);
            }


            // Check Collisions between ball and any blocks on the screen
            boolean flipX = false; // Should X direction be flipped
            boolean flipY = false;
            for (int i = 0; i < grid.getGrid().length; i++) {
                for (int j = 0; j < grid.getGrid()[i].length; j++) {
                    Block block = grid.getGrid()[i][j];

                    if (block == null) {
                        continue;
                    }

                    EdgeHit ballBlockHit = CollisionChecker.checkCollision(block, ball);

                    if (ballBlockHit == EdgeHit.XAXIS) {
                        flipX = true;
                        grid.removeBlock(i, j);
                        Sound.playSound(Sound.getRandomHitSound());

                    } else if (ballBlockHit == EdgeHit.YAXIS) {
                        flipY = true;
                        grid.removeBlock(i, j);
                        Sound.playSound(Sound.getRandomHitSound());

                    } else if (ballBlockHit == EdgeHit.BOTH) {
                        flipX = true;
                        flipY = true;
                    }
                }
            }
            if (flipX) ball.flipVelX();
            if (flipY) ball.flipVelY();


            // Call the ball onTick function for it to move.
            ball.onTick();
        }
    }

    private double[] calculateNewXVelocityAfterPaddleHit(Ball ball) {
        // Getting the middle of the paddle
        double paddleMiddlePosX = this.paddle.getPosX() + paddle.getWidth()/2;
        // Ball position when hitting the paddle
        double ballHitPosX = ball.getPosX();

        double decrease = 182; // 0.7 * x = 128, where 128 = (paddle width)/2

        //System.out.println("Before x: " + ball.getVelX() + " y: " + ball.getVelY() + " total: " + (Math.abs(ball.getVelX()) + Math.abs(ball.getVelY())));
        // Calculating velocities
        double velX = (paddleMiddlePosX - ballHitPosX) / decrease;

        // Prevent it from going too straight
        double min = 0.25;
        if (velX < 0 && velX > -min) velX -= min;
        else if (velX > 0 && velX < min) velX += min;

        double velY = maxAddedVel - Math.abs(velX);

        //System.out.println("After x: " + (-velX) + " y: " + (-velY) + " total: " + (velX+velY));
        return new double[]{-velX, -velY};
    }

    public void resetBallAndPaddle(){
        //relocate paddle
        this.paddle.setPosX(WindowUtils.getWindowWidth()/2 - paddle.getWidth()/2);
        this.paddle.getNode().relocate(WindowUtils.getWindowWidth()/2 - paddle.getWidth()/2, WindowUtils.getWindowHeight() * 0.8);

        // Reset ball position and velocity
        int radius = 16;
        double[] vel = calculateStartVelForBall();

        Ball ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth()/2 - (int) (radius/2)  , this.paddle.getPosY() - 3* paddle.getHeight() , vel[0], vel[1], radius);
        balls.add(ball);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth()/2 - (int) (radius/2)  , this.paddle.getPosY() - 2* paddle.getHeight() );

    }

}