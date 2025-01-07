package breakoutbasic.objects;

import breakoutbasic.scenes.PlayScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Entity {
    private double velX;
    private double velY;
    private Image img;
    private ImageView imgView = new ImageView();

    public Ball(PlayScene playScene, double posX, double posY, double velX, double velY, double size/*, String imgPath*/){
        super(posX, posY, size, size);
        this.velX = velX;
        this.velY = velY;

        /*this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(size);
        imgView.setFitWidth(size);*/

        Circle ball = new Circle(this.getPosX(), this.getPosY(), this.getHeight());
        ball.setFill(Color.RED);
        playScene.getPane().getChildren().add(ball);
    }

    public void updatePosX() {
        this.setPosX(this.getPosX() + velX);
    }

    public void updatePosY() {
        this.setPosY(this.getPosY() + velY);
    }

    public double getVelX(){
        return velX;
    }

    public double getVelY(){
        return velY;
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

}
