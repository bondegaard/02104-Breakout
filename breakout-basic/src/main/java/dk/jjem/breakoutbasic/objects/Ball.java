package dk.jjem.breakoutbasic.objects;

import javafx.scene.image.Image;

public class Ball {
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private final double size;
    private Image img;


    public Ball(double posX, double posY, double velX, double velY, double size, Image img){
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.size = size;
        this.img = img;

        this.img.
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

    public void setImg(Image newImg){
        img = newImg;
    }

    public Image getImg(){
        return img;
    }

    public 
}
