package dk.jjem.breakoutbasic;

import dk.jjem.breakoutbasic.loop.GameLoop;
import dk.jjem.breakoutbasic.scenes.AbstractScene;
import dk.jjem.breakoutbasic.scenes.PlayScene;
import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.*;

import java.io.IOException;

public class Breakout extends Application {

    private AbstractScene currentScene;

    private GameLoop gameLoop;

    public Breakout run() {
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

        // Setup Grid
       // new Grid(5,10);

        // Start Game Loop
        gameLoop = new GameLoop(this::onTick);
        gameLoop.start();
        WindowUtils.getPrimaryStage().setOnCloseRequest(event -> { if (gameLoop != null) gameLoop.stop();});
    }

    private void setupPlayScene() {
        // Setup Canvas
        Canvas canvas = new Canvas();

        // Setup Group to have canvas
        Group group = new Group();
        group.getChildren().add(canvas);

        // Setup Scene
        Scene scene = new Scene(group);
        scene.setFill(Color.BLACK);

        // Set Current Scene
        this.currentScene = new PlayScene(scene, canvas);
        this.currentScene.displayScene();
    }

    public void onTick() {

    }
}