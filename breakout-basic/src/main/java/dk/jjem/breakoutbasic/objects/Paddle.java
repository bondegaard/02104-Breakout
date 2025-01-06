
package dk.jjem.breakoutbasic.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    private double posX;
    private double posY;
    private double velX;
    private final double size;
    private Image img;
    private ImageView imgView = new ImageView();

    public Paddle(double posX, double posY, double velX, double size, String imgPath){
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.size = size;

        this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(size);
        imgView.setFitHeight(size);
    }

    public void updatePosXRight() {
        posX += velX;
    }

    public void updatePosXLeft() {
        posX -= velX;
    }


    public void setPosX(int newPosX){
        posX = newPosX;
    }

    public void setPosY(int newPosY){
        posY = newPosY;
    }

    public double getPosX(){
        return posX;
    }

    public double getPosY(){
        return posY;
    }

    public double getVelX(){
        return velX;
    }

    public double getSize() {
        return size;
    }

    public void setImg(String newImgPath){
        img = new Image(newImgPath, Math.round(posX), Math.round(posY), false, false);
        imgView.setImage(img);
    }

    public Image getImg(){
        return img;
    }

    public ImageView getImgView() {
        return imgView;
    }

}

