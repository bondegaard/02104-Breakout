package breakoutadvance.scenes;

import breakoutadvance.Breakout;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Fonts;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * Abstract class to handle different kinds of scenes
 */
public abstract class AbstractScene {
    protected Scene scene; // Scene that is displayed

    protected Pane pane; // Pane which spands the full screen.

    protected Font currentFont = Fonts.getFont(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf");


    /**
     * Create a basic Scene
     */
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

    /**
     * Display scene on the current stage
     */
    public void displayScene() {
        // Adding Scene to Stage
        WindowUtils.getPrimaryStage().setScene(scene);
        WindowUtils.getPrimaryStage().show();
    }

    /**
     * A function that is called every tick.
     */
    public abstract void onTick();
}
