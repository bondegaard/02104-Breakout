package breakoutadvance.objects.powerups;

import breakoutadvance.objects.Powerup;
import breakoutadvance.objects.powerups.PowerupType;
import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;

public class PlusWidthPowerUp extends Powerup {
    /**
     * Basic setup for an entity
     *
     * @param playScene current play scene
     * @param posX      x position
     * @param posY      y position
     * @param height    height of entity
     * @param width     width of entity
     * @param velX
     * @param velY
     */
    public PlusWidthPowerUp(PlayScene playScene, double posX, double posY, double height, double width, double velX, double velY) {
        super(playScene, PowerupType.PLUS_ONE, posX, posY, height, width, velX, velY);
    }

    @Override
    public void onCollision() {
        if (hasCollided) return;
        hasCollided = true;
        this.playScene.setPaddleWidth();
    }
}
