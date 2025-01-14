package breakoutadvance.objects;

import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.scenes.PlayScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Powerup extends AbstractEntity {

    protected final PlayScene playScene;
    protected boolean hasCollided = false;
    private final double velX;
    private double velY;
    private Image img;
    private ImageView imgView = new ImageView();

    /**
     * Basic setup for an entity
     *
     * @param posX   x position
     * @param posY   y position
     * @param height height of entity
     * @param width  width of entity
     */
    public Powerup(PlayScene playScene, PowerupType type, double posX, double posY, double height, double width, double velX, double velY) {
        super(posX, posY, height, width);
        this.playScene = playScene;
        this.velX = velX;
        this.velY = velY;

        try {
            Image image = type.getImg();
            imgView = new ImageView(image);
            imgView.relocate(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight());
            imgView.setFitHeight(this.getHeight() * 4);
            imgView.setFitWidth(this.getWidth() * 4);

            playScene.getPane().getChildren().add(imgView);
            this.setNode(imgView);
        } catch (Exception e) {
            // Drawing ball
            // Adding radius (this.getHeight()) to accommodate how a circle is drawn
            Circle ball = new Circle(this.getPosX(), this.getPosY(), this.getHeight() / 2);
            ball.setFill(Color.YELLOW);
            playScene.getPane().getChildren().add(ball);

            this.setNode(ball);
        }
    }

    //sets new X , Y position
    public void updatePosition() {
        this.setPosX(this.getPosX() + velX);
        this.setPosY(this.getPosY() + velY);
        this.getNode().relocate(this.getPosX(), this.getPosY()); //relocates points in scene
    }


    public void onTick(double speed) {
        updatePosition();
        velY += speed;
    }

    public abstract void onCollision();
}
