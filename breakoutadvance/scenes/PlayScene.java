package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.grid.Grid;
import breakoutadvance.objects.*;
import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.persistentdata.data.Data;
import breakoutadvance.persistentdata.data.Game;
import breakoutadvance.persistentdata.data.GameOutCome;
import breakoutadvance.utils.*;
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

    private List<Powerup> powerups = new ArrayList<>();

    private Paddle paddle;

    private Text startOrPauseText;

    private Text infoText;

    private Text addInfoText;

    private Random random = new Random();

    private final double maxAddedVel = 1.0;

    private int lives = 4;
    private Text deathPauseText;
    private Text deathInfoText;
    private boolean died = false;

    public int score = 0;

    private final LifesDisplay lifesDisplay;


    public PlayScene(int n, int m) {
        AssetManager.getInstance().reloadAllImages();
        this.addBackgroundImage();
        this.grid = new Grid(this, 4, 6);

        // Create ball and paddle
        this.paddle = new Paddle(this, WindowUtils.getWindowWidth()/2 - ((double) Constants.PADDLE_WIDTH /2), WindowUtils.getWindowHeight() * 0.8, 1.0, Constants.PADDLE_HEIGHT, Constants.PADDLE_WIDTH);

        // Calculating angle/velocity
        spawnBall();

        // Add start or pause text
        addStartOrPauseText();

        // Add death note text
        addDeathPauseText();

        // Setup Keyboard events
        setupKeyPressedEvents();

        // Add lives
        this.lifesDisplay = new LifesDisplay();
        this.lifesDisplay.updateLives(this, lives);

    }

    public void addBackgroundImage(){
        this.getScene().setFill(Color.BLACK);
        try {

            FileInputStream input = new FileInputStream("./assets/img/Background2Færdig2.png");
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
            resetBallAndPaddle();
            this.grid = new Grid(this, 4, 6);

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

                    //reset and update paddle width of paddle when die
                    Constants.PADDLE_WIDTH = 200;
                    this.paddle.getNode().relocate(this.paddle.getPosX(), this.paddle.getPosY());
                    this.paddle.getImgView().setFitWidth(Constants.PADDLE_WIDTH);
                    this.paddle.setWidth(Constants.PADDLE_WIDTH);


                    // Prettier
                    if (lives == 1)
                        this.deathPauseText.setText("You Died! You have " + lives + " life left.");
                    else
                        this.deathPauseText.setText("You Died! You have " + lives + " lives left.");

                    died = true;
                    playing = !playing;
                    resetBallAndPaddle();
                    if (lives <= 0) {
                      Breakout.getInstance().setCurrentScene(new GameOverScene());
                      Sound.playSound(Sound.LOSE);

                        // Save new highscore
                        Data data = Breakout.getInstance().getDataManager().getData();
                        if (data.getHighscore() < this.score)
                            data.setHighscore(this.score);

                        data.addGame(new Game(this.score, GameOutCome.LOSE));
                        Breakout.getInstance().getDataManager().saveData();
                    }
                }
                this.lifesDisplay.updateLives(this, lives);
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
                //double[] vel = calculateNewXVelocityAfterPaddleHit(ball);

                ball.setPosY(this.paddle.getPosY() - ball.getHeight() * 2); // Making sure the ball only hits the paddle once
                //ball.setVelY(-Math.abs(ball.getVelY()));
                double[] vel = calculateNewXVelocityAfterPaddleHit(ball);
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
                        attemptPowerupSpawn(block.getPosX()+block.getWidth()/2, block.getPosY()+block.getHeight()/2);

                        grid.removeBlock(i, j);
                        Sound.playSound(Sound.getRandomHitSound());
                    } else if (ballBlockHit == EdgeHit.YAXIS) {
                        flipY = true;
                        attemptPowerupSpawn(block.getPosX()+block.getWidth()/2, block.getPosY()+block.getHeight()/2);


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


        // Remove powerups under screen or collides with paddle
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();
            if (powerup.getPosY() > WindowUtils.getWindowHeight()) {
                this.getPane().getChildren().remove(powerup.getNode());
                powerupIterator.remove();
                continue;
            }
            if(CollisionChecker.checkCollision(this.paddle, powerup)) {
                this.getPane().getChildren().remove(powerup.getNode());
                powerupIterator.remove();
                powerup.onCollision();
            }
        }

        powerups.forEach(Powerup::onTick);
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

        // Prevent it from going too straight horizontally and vertically
        double min = 0.25;
        double max = 0.75;
        if (velX < 0 && velX > -min) velX -= min;
        else if (velX > 0 && velX < min) velX += min;
        else if (velX > max) velX -= (velX-max);
        else if (velX < -max) velX += (velX-max);

        double velY = maxAddedVel - Math.abs(velX);

        //System.out.println("After x: " + (-velX) + " y: " + (-velY) + " total: " + (velX+velY));
        return new double[]{-velX, -velY};
    }

    public void resetBallAndPaddle(){
        // remove all balls
        this.balls.forEach(ball -> this.getPane().getChildren().remove(ball.getNode()));
        this.balls.clear();

        // remove all powerups
        this.powerups.forEach(powerup -> this.getPane().getChildren().remove(powerup.getNode()));
        this.powerups.clear();

        //relocate paddle
        this.paddle.setPosX(WindowUtils.getWindowWidth()/2 - paddle.getWidth()/2);
        this.paddle.getNode().relocate(WindowUtils.getWindowWidth()/2 - paddle.getWidth()/2, WindowUtils.getWindowHeight() * 0.8);

        spawnBall();
    }

    public Grid getGrid() {
        return grid;
    }

    public void spawnBall() {
        // Reset ball position and velocity
        double[] vel = calculateStartVelForBall();

        Ball ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth()/2 - (int) (Constants.BALL_RADIUS /2)  , this.paddle.getPosY() - 3* paddle.getHeight() , vel[0], vel[1], Constants.BALL_RADIUS);
        balls.add(ball);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth()/2 - (int) (Constants.BALL_RADIUS /2)  , this.paddle.getPosY() - 2* paddle.getHeight() );
    }

    public void increasePaddleWidth(){
        if(Constants.PADDLE_WIDTH <= 414 ){

            //increase and update paddle Width
            Constants.PADDLE_WIDTH *= 1.2;

            //sets paddle Width
            this.paddle.getNode().relocate(this.paddle.getPosX(), this.paddle.getPosY());
            this.paddle.getImgView().setFitWidth(Constants.PADDLE_WIDTH);
            this.paddle.setWidth(Constants.PADDLE_WIDTH);
        }
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

    public void attemptPowerupSpawn(double xPos, double yPos) {
        for (PowerupType type : PowerupType.values()) {
            int randomNumber = random.nextInt(1000);
            if (randomNumber < type.getSpawnChance()) { // Convert spawn chance to a comparable value
                Powerup powerup = type.createPowerup(this, type,xPos, yPos, 16, 16, 0, 0.01);
                powerups.add(powerup);
                break;
            }
        }
    }
}
