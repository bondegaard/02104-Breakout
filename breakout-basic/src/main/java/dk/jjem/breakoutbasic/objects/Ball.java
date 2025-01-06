package dk.jjem.breakoutbasic.objects;

public class Ball {
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private double size;

    public Ball(double posX, double posY, double velX, double velY, double size){
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        this.velY = velY;
        this.size = size;
    }

    public void updatePosX() {
        posX += velX;
    }

    public void updatePosY() {
        posY += velY;
    }

    

}
