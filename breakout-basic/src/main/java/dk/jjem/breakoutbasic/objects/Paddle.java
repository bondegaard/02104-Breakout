
package dk.jjem.breakoutbasic.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    private double posX;
    private double posY;
    private double velX;
    private final double height;
    private final double width;
    private Image img;
    private ImageView imgView = new ImageView();

    public Paddle(double posX, double posY, double velX, double height, double width, String imgPath){
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.width = width;
        this.height = height;

        this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(height);
        imgView.setFitWidth(width);
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

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
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

