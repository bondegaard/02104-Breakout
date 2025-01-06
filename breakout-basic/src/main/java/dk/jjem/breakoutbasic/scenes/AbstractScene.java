package dk.jjem.breakoutbasic.scenes;

import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.scene.Scene;

public abstract class AbstractScene {
    private Scene scene;

    public AbstractScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    public void displayScene() {
        // Adding Scene to Stage
        WindowUtils.getPrimaryStage().setScene(scene);
        WindowUtils.getPrimaryStage().show();
    }
}
