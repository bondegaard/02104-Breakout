package breakoutadvance.objects.powerups;

import breakoutadvance.Breakout;
import breakoutadvance.objects.Powerup;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;

public enum PowerupType {
    PLUS_ONE(Images.getImage(Constants.BALL_FILEPATH + Breakout.getInstance().getDataManager().getData().getBallColor() + ".png"), 200), // 1 is equal to 0.1%
    PLUS_WIDTH(Images.getImage("assets/img/paddleWidthPowerUp.png"), 200),
    BOMB(Images.getImage("assets/img/bomb.png"), 100);


    private final Image img;

    private final double spawnChance;

    PowerupType(Image img, double spawnChance) {
        this.img = img;
        this.spawnChance = spawnChance;
    }

    public Image getImg() {
        return img;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public Powerup createPowerup(PlayScene playScene, PowerupType type, double posX, double posY, double height, double width, double velX, double velY) {
        switch (type) {
            case PLUS_ONE:
                return new PlusOnePowerUp(playScene, posX, posY, height, width, velX, velY);
            case PLUS_WIDTH:
                return new PlusWidthPowerUp(playScene, posX, posY, height, width, velX, velY);
            case BOMB:
                return new BombObstacle(playScene, posX, posY, height, width, velX, velY);
            default:
                throw new IllegalArgumentException("Invalid powerup type");
        }
    }
}



