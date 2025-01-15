package breakoutadvance.core;

import breakoutadvance.Breakout;
import breakoutadvance.levels.Level;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.Block;
import breakoutadvance.objects.Paddle;
import breakoutadvance.objects.Powerup;
import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.persistentdata.data.Data;
import breakoutadvance.scenes.GameOverScene;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game {

    private final PlayScene playScene;
    private final List<Ball> balls = new ArrayList<>();
    private final List<Powerup> powerups = new ArrayList<>();
    private final Paddle paddle;
    private final Random random = new Random(); // Random generator used for direction of the ball when calculating the velocity
    private boolean playing = false;
    private Grid grid;
    private int lives;
    private int score = 0;
    private boolean died = false;
    private Level level;

    public Game(PlayScene playScene) {
        this.playScene = playScene;

        // Set level variable to the current level
        this.level = Breakout.getInstance().getLevelManager().getCurrentLevel();

        // Setup current grid of blocks
        this.grid = new Grid(this, this.level.getLevelMap());

        // Creating paddle
        this.paddle = new Paddle(playScene, WindowUtils.getWindowWidth() / 2 - (this.level.getPaddleWidth() / 2), WindowUtils.getWindowHeight() * 0.8, this.level.getPaddleSpeed(), Constants.PADDLE_HEIGHT, this.level.getPaddleWidth());

        // Set Hearts
        this.lives = Constants.STARTING_HEARTS;

        // Spawn ball
        spawnBall();
    }

    /**
     * When the player dies, 1 will be subtracted from the players lives.
     * It will also display how many lives you have left, while updating the visual hearts in the bottom right
     * Furthermore it will save a potential highscore
     */
    public void hasDied() {
        lives--;
        this.playScene.getLifesDisplay().updateLives(this.playScene, lives);

        //reset and update paddle width of paddle when die
        resetBallAndPaddle();

        // Prettier
        if (lives == 1)
            this.playScene.getDeathPauseText().setText("You Died! You have " + lives + " life left.");
        else
            this.playScene.getDeathPauseText().setText("You Died! You have " + lives + " lives left.");

        died = true;
        playing = false;
        if (lives <= 0) {
            // Save new highscore
            Data data = Breakout.getInstance().getDataManager().getData();
            if (data.getHighscore() < this.score) {
                data.setHighscore(this.score);

                data.addGame(new breakoutadvance.persistentdata.data.Game(this.score));
                Breakout.getInstance().getDataManager().saveData();
                return;
            }

            Breakout.getInstance().setCurrentScene(new GameOverScene());
            Sound.playSound(Sound.LOSE);
        }
    }

    public boolean checkVictory() {
        if (this.grid.getAliveAmount() <= 0) {
            Breakout.getInstance().getLevelManager().setNextLevel();
            this.level = Breakout.getInstance().getLevelManager().getCurrentLevel();
            resetBallAndPaddle();

            this.grid = new Grid(this, this.level.getLevelMap());

            Sound.playSound(Sound.WON);

            // Save new highscore
            Data data = Breakout.getInstance().getDataManager().getData();
            if (data.getHighscore() < this.score)
                data.setHighscore(this.score);

            Breakout.getInstance().getDataManager().saveData();
            return true;
        }
        return false;
    }

    public void checkBallOutOfBounds() {
        // Collect balls to be removed
        List<Ball> toRemove = balls.stream()
                .filter(ball -> ball.getPosY() >= WindowUtils.getWindowHeight())
                .toList();

        // Remove all collected balls
        for (Ball ball : toRemove) {
            balls.remove(ball); // Remove from the balls list
            this.getPlayScene().getPane().getChildren().remove(ball.getNode()); // Remove from JavaFX pane
        }

        // Check if all balls are removed
        if (balls.isEmpty()) {
            hasDied();
        }
    }


    public void checkBallPaddleCollision() {
        for (Ball ball : balls) {
            EdgeHit ballPaddleHit = CollisionChecker.checkCollision(this.paddle, ball);
            if (ballPaddleHit == EdgeHit.YAXIS) {

                ball.setPosY(this.paddle.getPosY() - ball.getHeight() * 2); // Making sure the ball only hits the paddle once
                double[] vel = calcNewVelAfterPaddleColl(ball);
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
                        attemptPowerupSpawn(block.getPosX() + block.getWidth() / 2, block.getPosY() + block.getHeight() / 2);

                        grid.removeBlock(i, j);
                        Sound.playSound(Sound.getRandomHitSound());
                    } else if (ballBlockHit == EdgeHit.YAXIS) {
                        flipY = true;
                        attemptPowerupSpawn(block.getPosX() + block.getWidth() / 2, block.getPosY() + block.getHeight() / 2);

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
        }
    }

    public void checkPowerupCollision() {
        Iterator<Powerup> powerupIterator = powerups.iterator();
        while (powerupIterator.hasNext()) {
            Powerup powerup = powerupIterator.next();

            // Check out of bounds
            if (powerup.getPosY() > WindowUtils.getWindowHeight()) {
                this.playScene.getPane().getChildren().remove(powerup.getNode());
                powerupIterator.remove();
                continue;
            }

            // Check collision with paddle
            if (CollisionChecker.checkCollision(this.paddle, powerup)) {
                this.playScene.getPane().getChildren().remove(powerup.getNode());
                powerupIterator.remove();
                powerup.onCollision();
            }
        }
    }

    /**
     * Calculating a new velocity based on where the ball hits the paddle
     *
     * @param ball The ball which hit the paddle
     * @return Returning an array with a new x-vel and y-vel
     */
    private double[] calcNewVelAfterPaddleColl(Ball ball) {
        // Getting the middle of the paddle
        double paddleMiddlePosX = this.paddle.getPosX() + paddle.getWidth() / 2;
        // Ball position when hitting the paddle
        double ballHitPosX = ball.getPosX();

        // x = y / 0.75
        // y = (paddle width) / 2
        double scaling = (this.paddle.getWidth() / 2.0) / 0.75;

        // Calculating velocities
        double velX = (paddleMiddlePosX - ballHitPosX) / scaling;

        // Prevent it from going too straight horizontally and vertically
        double min = 0.25 * this.level.getBallSpeed();
        double max = 0.75 * this.level.getBallSpeed();
        if (velX < 0 && velX > -min) velX = -min;
        else if (velX > 0 && velX < min) velX = min;
        else if (velX > max) velX = max;
        else if (velX < -max) velX = -max;

        // Set new y-velocity compared to x-velocity
        double velY = this.level.getMaxBallVelocity() - Math.abs(velX);
        if (velY < 0) velY = -velY;

        // Prevent it from going too straight horizontally and vertically
        if (velY == 0) velY = Constants.MINIMUN_BALL_Y_VELOCITY;
        else if (velY < 0) {
            velY = Math.min(velY, -Constants.MINIMUN_BALL_Y_VELOCITY);
        } else if (velY > 0) {
            velY = Math.max(velY, Constants.MINIMUN_BALL_Y_VELOCITY);
        }

        return new double[]{-velX, -velY};
    }

    /**
     * After the player has died, or a new level has begun, the ball and the paddle will spawn at the middle
     */
    public void resetBallAndPaddle() {
        // remove all balls
        this.balls.forEach(ball -> this.playScene.getPane().getChildren().remove(ball.getNode()));
        this.balls.clear();

        // remove all powerups
        this.powerups.forEach(powerup -> this.playScene.getPane().getChildren().remove(powerup.getNode()));
        this.powerups.clear();

        //relocate paddle
        this.paddle.setWidth(this.level.getPaddleWidth());
        this.paddle.getImgView().setFitWidth(this.paddle.getWidth());
        this.paddle.setVelX(this.level.getPaddleSpeed());
        this.paddle.setPosX(WindowUtils.getWindowWidth() / 2 - paddle.getWidth() / 2);
        this.paddle.getNode().relocate(WindowUtils.getWindowWidth() / 2 - paddle.getWidth() / 2, WindowUtils.getWindowHeight() * 0.8);

        spawnBall();
    }

    /**
     * Spawning the ball
     */
    public void spawnBall() {
        // Reset ball position and velocity
        double[] vel = calculateStartVelForBall();

        Ball ball = new Ball(playScene, this.paddle.getPosX() + paddle.getWidth() / 2 - (Constants.BALL_RADIUS / 2), this.paddle.getPosY() - 3 * paddle.getHeight(), vel[0], vel[1], Constants.BALL_RADIUS);
        balls.add(ball);
        ball.getNode().relocate(this.paddle.getPosX() + paddle.getWidth() / 2 - (Constants.BALL_RADIUS / 2), this.paddle.getPosY() - 2 * paddle.getHeight());
    }

    /**
     * Increasing paddle width if its not already at max width
     */
    public void increasePaddleWidth() {
        if (paddle.getWidth() <= WindowUtils.getWindowWidth() * Constants.MAX_PADDLE_SIZE_TO_SCREEN) {

            //increase and update paddle Width
            this.paddle.setWidth(this.paddle.getWidth() * Constants.PADDLE_EXTEND_PER_POWERUP);

            //relocate and set new paddle
            this.paddle.getNode().relocate(this.paddle.getPosX(), this.paddle.getPosY());

            // Update the paddle image
            this.paddle.setImg(PaddleUtil.buildPaddleImage((int) this.paddle.getWidth()));
            this.paddle.getImgView().setFitWidth(this.paddle.getWidth());
        }
    }

    /**
     * When a bomb has been hit, the player loses a life, and a animation will be played
     * The game will also wait 200 ms, to show th bomb animation
     *
     * @param posX Where it hit on the x-axis
     * @param posY Where it hit on the y-axis
     */
    public void hitBombObstacle(double posX, double posY) {
        // Give Bomb animation enough time to play
        playing = false;
        died = true;
        this.playScene.getLifesDisplay().updateLives(playScene, lives);
        new BombExplosion(posX, posY, this.playScene.getPane());

        GameLoop.wait(200, this::hasDied);
    }

    /**
     * Calculating a random start velocity/angle for the ball
     *
     * @return Returning the start x- and y-velocity
     */
    private double[] calculateStartVelForBall() {
        // Creating a random angle to start from
        // Interval for velX is 0.2 to 0.75, and it varies from a negative and a positive number
        // velY is calculated based on (maxVel - velX)
        double ballSpeed = this.level.getBallSpeed();
        boolean positiveNumber = random.nextBoolean();
        double velX = random.nextDouble(0.2, 0.75) * ballSpeed;
        double velY = this.level.getMaxBallVelocity() - velX;
        velX = (positiveNumber) ? velX : -velX;
        velY = Math.max(Constants.MINIMUN_BALL_Y_VELOCITY, velY);

        return new double[]{velX, -velY};
    }

    /**
     * Attempting to spawn a powerup
     *
     * @param xPos Where to spawn it on the x-axis
     * @param yPos Where to spawn it on the y-axis
     */
    public void attemptPowerupSpawn(double xPos, double yPos) {
        for (PowerupType type : PowerupType.values()) {
            int randomNumber = random.nextInt(1000);
            if (randomNumber < type.getSpawnChance()) { // Convert spawn chance to a comparable value
                Powerup powerup = type.createPowerup(playScene, type, xPos, yPos, 8, 8, 0, 0.01);
                if (powerup == null) break;
                powerups.add(powerup);
                break;
            }
        }
    }


    /*
        # ######################### #
        # GETTERS AND SETTERS BELOW #
        # ######################### #
     */

    public PlayScene getPlayScene() {
        return playScene;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Set lives and update the display
     *
     * @param lives number of lives
     */
    public void setLives(int lives) {
        this.lives = lives;
        this.playScene.getLifesDisplay().updateLives(this.playScene, lives);
    }

    public int getScore() {
        return score;
    }

    /**
     * Set score and update the display
     *
     * @param score number of score
     */
    public void setScore(int score) {
        this.score = score;
        this.playScene.getDisplayScoreText().setText("Score: " + this.score);
    }

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void toggleDeath() {
        died = !died;
    }

    public void togglePlaying() {
        playing = !playing;
    }

    /**
     * Add score and update the display
     *
     * @param score number of score
     */
    public void increaseScore(int score) {
        this.score += score;
        this.playScene.getDisplayScoreText().setText("Score: " + this.score);
    }

    /**
     * Add one to health and update the display
     */
    public void increaseHealth() {
        lives++;
        this.playScene.getLifesDisplay().updateLives(this.playScene, lives);
    }
}
