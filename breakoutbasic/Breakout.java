package breakoutbasic;

import breakoutbasic.loop.GameLoop;
import breakoutbasic.scenes.AbstractScene;
import breakoutbasic.scenes.PlayScene;
import breakoutbasic.states.GameState;
import breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Breakout extends Application {

    public static Breakout instance;

    private AbstractScene currentScene;

    private GameLoop gameLoop;

    private GameState gameState = GameState.UNKNOWN;

    public Breakout run() {
        instance = this;
        launch();
        return this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Setup Stage
        primaryStage.setTitle("Breakout");
        
        // Get the screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        // Set the window size to the screen size (windowed fullscreen)
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

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

        if (currentScene instanceof PlayScene playScene) {
           // playScene.getCanvas();
        }
    }

    private void setupPlayScene() {
        // Set Current Scene
        this.currentScene = new PlayScene(3, 15);
        this.gameState = GameState.PLAY;
    }

    public void onTick() {
        if (currentScene != null) {
            currentScene.onTick();
        }
    }

    public static Breakout getInstance() {
        return instance;
    }

    public AbstractScene getCurrentScene() {
        return currentScene;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public GameState getGameState() {
        return gameState;
    }
}