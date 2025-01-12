package breakoutadvance.objects.powerups;

import breakoutadvance.objects.Powerup;
import breakoutadvance.scenes.PlayScene;

public enum PowerupType {
    PLUS_ONE("./assets/img/powerup_plus_one.png", 100), // 1 is equal to 0.1%
    PLUS_WIDTH("./assets/img/paddleWidthPowerUp.png", 1000);

    private final String imgPath;

    private final double spawnChance;

    PowerupType(String imgPath, double spawnChance) {
        this.imgPath = imgPath;
        this.spawnChance = spawnChance;
    }

    public String getImgPath() {
        return imgPath;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public Powerup createPowerup(PlayScene playScene, PowerupType type, double posX, double posY, double height, double width, double velX, double velY) {
        switch (type) {
            case PLUS_ONE:
                return new PowerUpExtraBall(playScene, posX, posY, height, width, velX, velY);
            case PLUS_WIDTH:
                return new PowerUpExpandPaddle(playScene, posX, posY, height, width, velX, velY);
            default:
                throw new IllegalArgumentException("Invalid powerup type");
        }
    }
}



