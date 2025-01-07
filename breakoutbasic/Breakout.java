package breakoutbasic;

import breakoutbasic.loop.GameLoop;
import breakoutbasic.scenes.AbstractScene;
import breakoutbasic.scenes.PlayScene;
import breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class Breakout extends Application {

    public static Breakout instance; // Instance of breakout

    private AbstractScene currentScene; // Current displayed scene

    private GameLoop gameLoop; // Gameloop which calls the onTick function.

    public Breakout run() {
        launch();
        return this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;
        // Setup Stage
        primaryStage.setTitle("Breakout");
        
        // Get the screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Set the window size to the screen size (windowed fullscreen)
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getMaxX());
        primaryStage.setHeight(screenBounds.getMaxY());

        // Disable resizing
        primaryStage.setResizable(false);

        // Adding Stage to window util
        WindowUtils.setPrimaryStage(primaryStage);

        // Setup Play Scene for Game
        setupPlayScene();

        // Start Game Loop
        gameLoop = new GameLoop(this::onTick);
        gameLoop.start();
        WindowUtils.getPrimaryStage().setOnCloseRequest(event -> { if (gameLoop != null) gameLoop.stop();});
    }

    /**
     * Setup starting PlayScene
     */
    private void setupPlayScene() {
        // Set Current Scene
        this.currentScene = new PlayScene(1, 1);
    }

    /**
     * Called every tick from the gameLoop
     */
    public void onTick() {
        if (this.currentScene != null) {
            this.currentScene.onTick();
        }
    }

    public static Breakout getInstance() {
        return instance;
    }

    public AbstractScene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(AbstractScene currentScene) {
        this.currentScene = currentScene;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }
}