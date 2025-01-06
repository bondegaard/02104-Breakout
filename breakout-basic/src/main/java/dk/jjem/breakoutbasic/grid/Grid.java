package dk.jjem.breakoutbasic.grid;

import dk.jjem.breakoutbasic.Breakout;
import dk.jjem.breakoutbasic.utils.WindowUtils;
import javafx.application.Application;

import java.awt.*;

public class Grid {

    private Rectangles[][] grid;

    public Grid (int n, int m) {
        // Long side of the rectangle's length, based on window size
        double lSize = (WindowUtils.getWindowWidth() * (10/12)) / m; // 2/12 is left for blank space

        // Short side of the rectangle's length, based on window size
        double sArea = WindowUtils.getWindowHeight() / 10; // Area where rectangles are placed
        double sSize = sArea / n;

        int posXStart = 0;
        int posYStart = (int) (WindowUtils.getWindowHeight() * (1/12));
        for (int i = posYStart; i < n; i += sSize) {
            for (int j = posXStart; j < m; j += lSize) {
                //new Rectangles(j, i, lSize, sSize);
            }
        }
        draw();
    }

    public void draw() {
        for (Rectangles rectangles[] : grid) {
            for (Rectangles blocks : rectangles) {
                Rectangle rectangle = new Rectangle(blocks.getPosX(), blocks.getPosY(), blocks.getLongSideSize(), blocks.getShortSideSize());
            }
        }
    }
}
