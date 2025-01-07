package breakoutbasic.objects;

import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Entity {
    private double velX;
    private double velY;
    private Image img;
    private ImageView imgView = new ImageView();

    public Ball(PlayScene playScene, double posX, double posY, double velX, double velY, double radius/*, String imgPath*/){
        super(posX, posY, radius, radius);
        this.velX = velX;
        this.velY = velY;

        /*this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(radius);
        imgView.setFitWidth(radius);*/

        Circle ball = new Circle(this.getPosX(), this.getPosY(), this.getHeight());
        ball.setFill(Color.RED);
        playScene.getPane().getChildren().add(ball);

        this.setNode(ball);
    }

    public void updateX() {
        if(this.getPosX() + velX >= WindowUtils.getWindowWidth() - this.getWidth()*2){
            this.flipVelX();
        } else if(this.getPosX() +  velX <= 0){
            this.flipVelX();
        }
    }

    public void updateY() {
        if(this.getPosY() + velY >= WindowUtils.getWindowHeight() - this.getHeight()*2){
            return;
        } else if (this.getPosY() + velY <= 0){
            this.flipVelY();
        }
    }

    public void updatePosition() {
        this.setPosX(this.getPosX() + velX);
        this.setPosY(this.getPosY() + velY);
        this.getNode().relocate(this.getPosX(), this.getPosY());
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

    public void flipVelX(){
        velX = -velX;
    }
    public void flipVelY(){
        velY = -velY;
    }

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


    public void onTick() {
        this.updateX();
        this.updateY();
        this.updatePosition();
    }
}
