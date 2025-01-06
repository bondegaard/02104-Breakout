package breakoutbasic.scenes;

import breakoutbasic.utils.WindowUtils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public abstract class AbstractScene {
    private Scene scene;

    private Pane pane;

    public AbstractScene() {
        this.pane = new Pane();
        this.scene = new Scene(this.pane);

        scene.setRoot(pane);

        displayScene();
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    public void displayScene() {
        // Adding Scene to Stage
        WindowUtils.getPrimaryStage().setScene(scene);
        WindowUtils.getPrimaryStage().show();
    }

    public abstract void onTick();
}
