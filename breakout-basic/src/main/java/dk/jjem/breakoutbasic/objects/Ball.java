package dk.jjem.breakoutbasic.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private final double size;
    private Image img;
    private ImageView imgView = new ImageView();

    public Ball(double posX, double posY, double velX, double velY, double size, String imgPath){
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.size = size;

        this.img = new Image(imgPath, Math.round(posX), Math.round(posY), false, false);

        imgView.setImage(img);
        imgView.setFitHeight(size);
        imgView.setFitWidth(size);
    }

    public void updatePosX() {
        posX += velX;
    }

    public void updatePosY() {
        posY += velY;
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

    public double getVelY(){
        return velY;
    }

    public void flipVelX(){
        velX = -velX;
    }
    public void flipVelY(){
        velY = -velY;
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
