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

    private final int animationTime = 200; // Animation time
    private final Random rand = new Random();
    private final Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.ORANGE, Color.ORANGERED};

    public BombExplosion(double posX, double posY, Pane pane) {

        int numberOfParticles = 150;
        ParallelTransition bombExplosion = new ParallelTransition();

        for (int i = 0; i < numberOfParticles; i++) {
            // Creating new particle, with a randomly selected color from the colors array
            Circle eParticle = new Circle(posX, posY, rand.nextInt(2,5), colors[rand.nextInt(3)]);

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

    private ScaleTransition createScaleTransition(Circle circle, double scaleFactor) {
        // Creating a scale transition, based on animationTime and the given object
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(animationTime), circle);

        // Changing the scale from 100% to a random given number (scaleFactor)
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);

        // Returning scaleTransition
        return scaleTransition;
    }

    private FadeTransition createFadeTransition(Circle circle) {
        // Creating a fade transition, based on animationTime * 2, since we want to see the particles all the time
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(animationTime *2), circle);

        // Changing the opacity from 100% to 0
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Returning fadeTransition
        return fadeTransition;
    }

    private TranslateTransition createTranslateTransition(Circle circle, double x, double y) {
        // Creating a translation transition, which changes the location of the particles
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(animationTime), circle);
        
        translateTransition.setByX(x);
        translateTransition.setByY(y);
        return translateTransition;
    }
}
