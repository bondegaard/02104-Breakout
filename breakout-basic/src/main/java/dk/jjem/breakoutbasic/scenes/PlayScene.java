package dk.jjem.breakoutbasic.scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

public class PlayScene extends AbstractScene {
    private Canvas canvas;


    public PlayScene(Scene scene, Canvas canvas) {
        super(scene);
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
