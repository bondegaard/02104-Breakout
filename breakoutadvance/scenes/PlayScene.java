package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.core.Grid;
import breakoutadvance.objects.*;
import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.persistentdata.data.Data;
import breakoutadvance.persistentdata.data.Game;
import breakoutadvance.persistentdata.data.GameOutCome;
import breakoutadvance.scenes.components.UIComponentFactory;
import breakoutadvance.utils.*;
import breakoutadvance.utils.BombExplosion;
import breakoutadvance.utils.CollisionChecker;
import breakoutadvance.utils.EdgeHit;
import breakoutadvance.utils.Fonts;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.Sound;
import breakoutadvance.utils.WindowUtils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    private int lives = 3;
    private Text deathPauseText;
    private Text deathInfoText;
    private boolean died = false;

    private Text displayScore;
    public int score = 0;


    private final LifesDisplay lifesDisplay;


    public PlayScene(int n, int m) {
        this.addBackgroundImage();
        this.grid = new Grid(this, 4, 20);

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

        // Add score
        addDisplayScore();


    }

    public void addBackgroundImage(){
        this.getScene().setFill(Color.BLACK);
        try {
            Image image = Images.getImage(Constants.BACKGROUND_FILEPATH + "background12.png");

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
        this.startOrPauseText = new Text("Press ENTER to start");
        this.startOrPauseText.setFont(Fonts.getFont(Constants.FONT_FILEPATH + "OpenSans-Regular.ttf"));
        this.startOrPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.startOrPauseText.setFill(Color.BLACK);
        this.startOrPauseText.setStroke(Color.LIGHTGRAY);
        this.startOrPauseText.setStrokeWidth(1.25);
        this.getPane().getChildren().add(this.startOrPauseText);

        this.infoText = new Text("Press 'a' to move left and 'd' to move right");
        this.infoText.setFont(Fonts.getFont(Constants.FONT_FILEPATH + "OpenSans-Regular.ttf"));
        this.infoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.infoText.setFill(Color.BLACK);
        this.infoText.setStroke(Color.LIGHTGRAY);
        this.infoText.setStrokeWidth(1.25);
        this.getPane().getChildren().add(this.infoText);

        this.addInfoText = new Text("When started, you can press ENTER to pause the game");
        this.addInfoText.setFont(Fonts.getFont(Constants.FONT_FILEPATH + "OpenSans-Regular.ttf"));
        this.addInfoText.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        this.addInfoText.setFill(Color.BLACK);
        this.addInfoText.setStroke(Color.LIGHTGRAY);
        this.addInfoText.setStrokeWidth(1.25);
        this.getPane().getChildren().add(this.addInfoText);

        // Manually set the positions initially
        addCenterTextListener(this.startOrPauseText, 2, 2);
        addCenterTextListener(this.infoText, 2, 1.8);
        addCenterTextListener(this.addInfoText, 2, 1.6);

        // Listen for window resize and update positions accordingly
     //   this.getScene().widthProperty().addListener((observable, oldValue, newValue) -> updateTextPositions());
     //   this.getScene().heightProperty().addListener((observable, oldValue, newValue) -> updateTextPositions());
    }

    private void addCenterTextListener(Text text, double xOffset, double yOffSet) {
    if (text == null) return;

    text.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {

        // Make sure bounds of text are updated correctly before calculating new position
        Platform.runLater(() -> {
            double textWidth = newValue.getWidth();
            double textHeight = newValue.getHeight();

            text.setX((WindowUtils.getWindowWidth() - textWidth) / xOffset);
            text.setY((WindowUtils.getWindowHeight() - textHeight) / yOffSet);
        });
    });
}

    public void addDeathPauseText() {
        // Text to display start or pause information
        this.deathPauseText = new Text("You Died. You have " + lives + " left.");
        this.deathPauseText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        this.deathPauseText.setFill(Color.BLACK);
        this.deathPauseText.setStroke(Color.LIGHTGRAY);
        //this.deathPauseText.setStrokeWidth(1.25); // idk why it stops working when this line is not commented
        this.getPane().getChildren().add(this.deathPauseText);
        this.deathPauseText.setVisible(false);

        // Text to display controls
        this.deathInfoText = new Text("Press Enter to start again");
        this.deathInfoText.setStyle("-fx-font-size: 32px;-fx-font-weight: bold;");
        this.deathInfoText.setFill(Color.BLACK);
        this.deathInfoText.setStroke(Color.LIGHTGRAY);
        this.deathInfoText.setStrokeWidth(1.25);
        this.getPane().getChildren().add(this.deathInfoText);
        this.deathInfoText.setVisible(false);


        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.deathPauseText, 2, 2);
        addCenterTextListener(this.deathInfoText, 2, 1.8);
    }

    public void addDisplayScore() {
        // Text to display start or pause information
        this.displayScore = new Text("Score: " + grid.getNewScore());
        this.displayScore.setFont(Font.font(currentFont.getFamily(), 80));
        this.displayScore.setFill(Color.BLACK);
        this.displayScore.setStroke(Color.LIGHTGRAY);
        this.displayScore.setStrokeWidth(1.5);
        this.displayScore.setVisible(true);
        this.getPane().getChildren().add(this.displayScore);


        // Center the text after it is added to the scene as it needs to be visible and text changes
        // This makes sure that it is centered no matter what
        addCenterTextListener(this.displayScore, 20, 1);
    }

    public void increaseHealth() {
        lives++;
        this.lifesDisplay.updateLives(this, lives);
    }

    public void hasDied(){
        lives--;
        this.lifesDisplay.updateLives(this, lives);

        //reset and update paddle width of paddle when die
        this.paddle.setWidth(Constants.PADDLE_WIDTH);
        this.paddle.getNode().relocate(this.paddle.getPosX(), this.paddle.getPosY());
        this.paddle.setImg(PaddleUtil.buildPaddleImage(Constants.PADDLE_WIDTH));
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
            if (data.getHighscore() < this.score) {
                data.setHighscore(this.score);

                data.addGame(new Game(this.score, GameOutCome.LOSE));
                Breakout.getInstance().getDataManager().saveData();
                return;
            }
        }
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
                    hasDied();
                }
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
        double paddleMiddlePosX = this.paddle.getPosX() + paddle.getWidth() / 2;
        // Ball position when hitting the paddle
        double ballHitPosX = ball.getPosX();

        // Started with this function, where paddlewidth was a constant factor
        //double decrease = 182; // 0.7 * x = 128, where 128 = (paddle width) / 2

        // Now using these calculations, where paddlewidth is dynamic

        // x = y / 0.7
        // y = (paddle widt) / 2
        double decrease = (this.getPaddleWidth() / 2.0) / 0.7;

        // Calculating velocities
        double velX = (paddleMiddlePosX - ballHitPosX) / decrease;

        // Prevent it from going too straight horizontally and vertically
        double min = 0.25;
        double max = 0.75;
        if (velX < 0 && velX > -min) velX -= min;
        else if (velX > 0 && velX < min) velX += min;
        else if (velX > max) velX -= (velX - max);
        else if (velX < -max) velX += (velX - max);

        double velY = maxAddedVel - Math.abs(velX);
        if (velY < 0) velY = -velY;

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

    public int getScore(){
        return score;
    }

    public Text getDisplayScore(){
        return displayScore;
    }

    public double getPaddleWidth(){
        return paddle.getWidth();
    }


    public void spawnBall() {
        // Reset ball position and velocity
        double[] vel = calculateStartVelForBall();

        Ball ball = new Ball(this, this.paddle.getPosX() + paddle.getWidth()/2 - (int) (Constants.BALL_RADIUS /2)  , this.paddle.getPosY() - 3* paddle.getHeight() , vel[0], vel[1], Constants.BALL_RADIUS);
        balls.add(ball);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth()/2 - (int) (Constants.BALL_RADIUS /2)  , this.paddle.getPosY() - 2* paddle.getHeight() );
    }

    // Increase paddle width
    public void increasePaddleWidth(){
        if(paddle.getWidth() <= WindowUtils.getWindowWidth()/4 ){

            //increase and update paddle Width
            this.paddle.setWidth(this.paddle.getWidth() * 1.2);

            //relocate and set new paddle
            this.paddle.getNode().relocate(this.paddle.getPosX(), this.paddle.getPosY());

            // Update the paddle image
            this.paddle.setImg(PaddleUtil.buildPaddleImage((int) this.paddle.getWidth()));
            this.paddle.getImgView().setFitWidth(this.paddle.getWidth());
        }
    }

    // Bomb kills you when hit
    public void hitBombObstacle(double posX, double posY){
        new BombExplosion(posX, posY, this.pane);
        hasDied();
        lifesDisplay.updateLives(this,lives);
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
                if (powerup == null) break;
                powerups.add(powerup);
                break;
            }
        }
    }
}
