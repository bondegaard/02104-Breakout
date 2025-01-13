package breakoutadvance.utils;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


import java.util.Random;

public class BombExplosion {

    private final int scaleDuration = 200; // Animation time
    private final Random rand = new Random();


    public BombExplosion(double posX, double posY, Pane pane) {

        int numberOfParticles = 150;
        ParallelTransition bombExplosion = new ParallelTransition();

        for (int i = 0; i < numberOfParticles; i++) {
            Circle eParticle = new Circle(posX, posY, rand.nextInt(2,5), getRandomColor()[rand.nextInt(3)]);

            // Scaling transition
            ScaleTransition scaling = createScaleTransition(eParticle, rand.nextDouble());

            // Fading transition
            FadeTransition fading = createFadeTransition(eParticle);

            // Adding translating transition, to make the particles move
            TranslateTransition translating = createTranslateTransition(
                    eParticle, rand.nextInt(200)-100, rand.nextInt(200)-100
            );

            // Combining the animations
            bombExplosion.getChildren().add(new ParallelTransition(scaling, fading, translating));

            // Adding it
            pane.getChildren().add(eParticle);

            // Removing the particles when explosion is done
            bombExplosion.setOnFinished(e -> pane.getChildren().remove(eParticle));
        }

        // Playing the animation
        bombExplosion.play();
    }

    private Color[] getRandomColor() {
        return new Color[]{Color.RED, Color.YELLOW, Color.ORANGE, Color.ORANGERED};
    }

    private ScaleTransition createScaleTransition(Circle circle, double scaleFactor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(scaleDuration), circle);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);
        return scaleTransition;
    }

    private FadeTransition createFadeTransition(Circle circle) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(scaleDuration*2), circle);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        return fadeTransition;
    }

    private TranslateTransition createTranslateTransition(Circle circle, double x, double y) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(scaleDuration), circle);
        translateTransition.setByX(x);
        translateTransition.setByY(y);
        return translateTransition;
    }
}
