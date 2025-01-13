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
    PLUS_WIDTH(Images.getImage(Constants.IMAGE_PATH + "paddleWidthPowerUp.png"), 200),
    BOMB(Images.getImage( Constants.IMAGE_PATH + "bomb.png"), 100),
    HEART(Images.getImage( Constants.IMAGE_PATH + "heart_full.png"), 25);


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
        return switch (type) {
            case PLUS_ONE -> new PowerUpExtraBall(playScene, posX, posY, height, width, velX, velY);
            case PLUS_WIDTH -> {
                if (playScene.getPaddleWidth() <= WindowUtils.getWindowWidth() / 4) {
                    yield new PowerUpExpandPaddle(playScene, posX, posY, height, width, velX, velY);
                }
                yield null;
            }
            case BOMB -> new BombObstacle(playScene, posX, posY, height, width, velX, velY);
            case HEART -> new HearthObstacle(playScene, posX, posY, height, width, velX, velY);
            default -> throw new IllegalArgumentException("Invalid powerup type");
        };
    }
}



