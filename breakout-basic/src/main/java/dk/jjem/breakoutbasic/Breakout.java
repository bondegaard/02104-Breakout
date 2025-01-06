package dk.jjem.breakoutbasic;

import dk.jjem.breakoutbasic.grid.Grid;
import dk.jjem.breakoutbasic.loop.GameLoop;
import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Breakout extends Application {

    private Scene scene;

    private Text text;

    private GameLoop gameLoop;

    public Breakout run() {
        launch();
        return this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Breakout");
        primaryStage.setMaximized(true);

        text = new Text();
        text.setText("test");
        text.setFill(Color.WHITE);
        BorderPane borderPane = new BorderPane(text);
        scene = new Scene(borderPane);
        scene.setFill(Color.BLACK);
        primaryStage.setScene(scene);

        // Adding it to window util
      //  WindowUtils.setPrimaryStage(primaryStage);
      //  new Grid(5,10);

        // Start Game Loop
        initGameLoop();

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> { if (gameLoop != null) gameLoop.stop();});
    }

    public void initGameLoop() {

        final long time = System.currentTimeMillis();
        gameLoop = new GameLoop(() -> {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - time;

            text.setText("Time: " + elapsedTime);
        });
        gameLoop.start();
    }
}