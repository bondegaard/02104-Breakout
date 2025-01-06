package dk.jjem.breakoutbasic.utils;

import javafx.stage.Stage;

public class WindowUtils {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static double getWindowHeight() {
        return primaryStage.getHeight();
    }

    public static double getWindowWidth() {
        return primaryStage.getWidth();
    }
}
