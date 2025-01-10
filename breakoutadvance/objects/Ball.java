package breakoutadvance.objects;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;

public class Ball extends Entity {
    private double velX;
    private double velY;
    private Image img;
    private ImageView imgView = new ImageView();

    //Ball constructor
    public Ball(PlayScene playScene, double posX, double posY, double velX, double velY, double radius/*, String imgPath*/){
        super(posX, posY, radius, radius);
        this.velX = velX;
        this.velY = velY;

        try {
            Image image = Images.getImage(Constants.BALL_FILEPATH + Breakout.getInstance().getDataManager().getData().getBallColor() + ".png");
            if (image == null) {
                throw new Exception("Image not found");
            }
            imgView = new ImageView(image);
            imgView.relocate(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight());
            imgView.setFitHeight(this.getHeight()*2);
            imgView.setFitWidth(this.getWidth()*2);

            playScene.getPane().getChildren().add(imgView);
            this.setNode(imgView);
        } catch (Exception e) {
            // Drawing ball
            // Adding radius (this.getHeight()) to accommodate how a circle is drawn
            Circle ball = new Circle(this.getPosX() + this.getHeight(), this.getPosY() + this.getHeight(), this.getHeight());
            ball.setFill(Color.RED);
            playScene.getPane().getChildren().add(ball);

            this.setNode(ball);
        }
    }

    /**
     * Update x postion of the ball
     */
    public void updateX() {
        if(this.getPosX() + velX >= WindowUtils.getWindowWidth() - this.getWidth()*2){
            this.flipVelX(); //flip velocity if ball goes out of bounds (sides)
        } else if(this.getPosX() +  velX <= 0){
            this.flipVelX(); //flip velocity if ball goes out of bounds (sides)
        }
    }

    /**
     * Update y postion of the ball
     */
    public void updateY() {
        if(this.getPosY() + velY >= WindowUtils.getWindowHeight() - this.getHeight()*2){
            return; // Do nothing as the ball hits the bottom of the screen.
        } else if (this.getPosY() + velY <= 0){
            this.flipVelY(); //flip velocity if ball goes out of bounds (top)
        }
    }

    //sets new X , Y position
    public void updatePosition() {
        this.setPosX(this.getPosX() + velX);
        this.setPosY(this.getPosY() + velY);
        this.getNode().relocate(this.getPosX(), this.getPosY()); //relocates points in scene
    }

    public double getVelX(){
        return velX;
    }

    public double getVelY(){
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    /**
     * Flip the direction of the x velocity
     */
    public void flipVelX(){
        velX = -velX;
    }

    /**
     * Flip the direction of the y velocity
     */
    public void flipVelY(){
        velY = -velY;
    }

    //-----------------------no usages --------------------
    public void setImg(String newImgPath){
        img = new Image(newImgPath, Math.round(this.getPosX()), Math.round(this.getPosY()), false, false);
        imgView.setImage(img);
    }

    public Image getImg(){
        return img;
    }

    public ImageView getImgView() {
        return imgView;
    }

    //updates ball position

    /**
     * Function called every tick which updates the position of the ball.
     */
    public void onTick() {
        this.updateX();
        this.updateY();
        this.updatePosition();
    }
}
