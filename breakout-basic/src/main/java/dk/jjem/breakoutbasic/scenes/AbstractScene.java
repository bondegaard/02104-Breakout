package dk.jjem.breakoutbasic.scenes;

import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public abstract class AbstractScene {
    private Scene scene;

    private StackPane stackPane;

    public AbstractScene() {
        this.stackPane = new StackPane();
        this.scene = new Scene(this.stackPane);

        scene.setRoot(stackPane);

        displayScene();
    }

    public Scene getScene() {
        return scene;
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void displayScene() {
        // Adding Scene to Stage
        WindowUtils.getPrimaryStage().setScene(scene);
        WindowUtils.getPrimaryStage().show();
    }
}
