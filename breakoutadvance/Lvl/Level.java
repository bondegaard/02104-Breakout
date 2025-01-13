package breakoutadvance.Lvl;

import breakoutadvance.objects.powerups.PowerupType;

public class Level {

    private double paddleWidth;

    private double ballSpeed;

    private PowerupType [] availablePowerUps;

    private int spawnChance;

    private double powerUpSpeed;

    private double powerUpWidth;

    private double powerUpHeight;

    private int hearts;

    private double paddleSpeed;



    public Level( double paddleWidth, double ballSpeed, PowerupType[] availablePowerUps, int spawnChance, double powerUpSpeed, double powerUpWidth, double powerUpHeight, int hearts, double paddleSpeed){
        this.paddleWidth = paddleWidth;
        this.ballSpeed = ballSpeed;
        this.spawnChance = spawnChance;
        this.powerUpSpeed = powerUpSpeed;
        this.powerUpWidth = powerUpWidth;
        this.powerUpHeight = powerUpHeight;
        this.hearts = hearts;
        this.paddleSpeed = paddleSpeed;

    }
}
