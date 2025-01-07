package breakoutbasic.objects;

public abstract class Entity {

    protected double posX;
    protected double posY;

    protected double height;
    protected double width;

    public Entity(double posX, double posY, double height, double width) {
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
