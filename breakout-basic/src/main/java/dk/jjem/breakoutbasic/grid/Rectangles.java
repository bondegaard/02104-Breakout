package dk.jjem.breakoutbasic.grid;

public class Rectangles {
    private int posX;
    private int posY;
    private int longSideSize;
    private int shortSideSize;
    public boolean alive;

    public Rectangles(int posX, int posY, int longSideSize, int shortSideSize) {
        this.posX = posX;
        this.posY = posY;
        this.longSideSize = longSideSize;
        this.shortSideSize = shortSideSize;

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getLongSideSize() {
        return longSideSize;
    }

    public int getShortSideSize() {
        return shortSideSize;
    }
}
