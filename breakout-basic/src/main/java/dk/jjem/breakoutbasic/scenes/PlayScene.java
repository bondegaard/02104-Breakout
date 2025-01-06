package dk.jjem.breakoutbasic.scenes;

import dk.jjem.breakoutbasic.grid.Grid;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PlayScene extends AbstractScene {

    private Grid grid;


    public PlayScene(int n, int m) {
        this.getScene().setFill(Color.BLACK);
        this.grid = new Grid(this, n, m);

        Text text = new Text();
        text.setText("Hello World");
        text.setFill(Color.WHITE);

        this.getStackPane().getChildren().add(text);
    }

    public Grid getGrid() {
        return grid;
    }
}
