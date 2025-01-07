
package breakoutbasic.objects;

import breakoutbasic.scenes.PlayScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Entity {
    private double velX;
    private Image img;
    private ImageView imgView = new ImageView();

    private boolean moveLeft = false;
    private boolean moveRight = false;

    public Paddle(PlayScene playScene, double posX, double posY, double velX, double height, double width /*, String imgPath*/){
        super(posX, posY, height, width);
        this.velX = velX;

        /*this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(height);
        imgView.setFitWidth(width);*/

        Rectangle paddle = new Rectangle(this.getPosX(), this.getPosY(), this.getWidth(), this.getHeight());
        paddle.setFill(Color.RED);
        playScene.getPane().getChildren().add(paddle);
    }

    public void updatePosXRight() {
        this.setPosX(this.getPosX() + velX);
    }

    public void updatePosXLeft() {
        this.setPosX(this.getPosX() - velX);
    }

    public double getVelX(){
        return velX;
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

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
}

