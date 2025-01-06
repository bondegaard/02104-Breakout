package dk.jjem.breakoutbasic;

import dk.jjem.breakoutbasic.loop.GameLoop;
import dk.jjem.breakoutbasic.scenes.AbstractScene;
import dk.jjem.breakoutbasic.scenes.PlayScene;
import dk.jjem.breakoutbasic.states.GameState;
import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        primaryStage.setMaximized(true);

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