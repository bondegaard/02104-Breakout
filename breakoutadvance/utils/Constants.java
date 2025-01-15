package breakoutadvance.utils;

import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class Constants {

    // Paddle constants
    public static final int PADDLE_WIDTH = 200;
    public static final int PADDLE_HEIGHT = 16;
    public static final double MAX_PADDLE_SIZE_TO_SCREEN = 0.25;
    public static final double PADDLE_EXTEND_PER_POWERUP = 1.2;
    public static final String[] PADDLE_COLORS = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};

    // Ball constants
    public static final int BALL_RADIUS = 16;
    public static final double MINIMUN_BALL_Y_VELOCITY = 0.2;
    public static final String[] BALL_COLORS = new String[]{"beige", "black", "blue", "blue2", "brown", "green", "pink", "red", "yellow"};

    // Block constants
    public static final int BLOCK_WIDTH = 128;
    public static final int BLOCK_HEIGHT = 16;

    // Asset paths
    public static final String IMAGE_PATH = "./assets/img/";
    public static final String BACKGROUND_FILEPATH = "./assets/img/backgrounds/";
    public static final String BALL_FILEPATH = "./assets/img/OpenGameArt/balls/";
    public static final String PADDLE_FILEPATH = "./assets/img/OpenGameArt/paddles/";
    public static final String BLOCK_FILEPATH = "./assets/img/OpenGameArt/bricks/";
    public static final String FONT_FILEPATH = "./assets/fonts/";

    // Font constants
    public static final String DEFAULT_FONT = FONT_FILEPATH + "BlackwoodCastle.ttf";
    public static final int DEFAULT_FONT_SIZE = 64;

    // Color constants
    public static final Color NORMAL_TEXT_COLOR = Color.WHITE;
    public static final Color HIGHLIGHT_TEXT_COLOR = Color.YELLOW;

    // Menu Background
    public static final String MENU_BACKGROUND = BACKGROUND_FILEPATH + "background2.png";

    // Starting hearts
    public static final int STARTING_HEARTS = 3;

    // Grid constants
    public static final int OFFSET_BETWEEN_BLOCKS = 3;

    // Layout constants
    public static final int DEFAULT_VBOX_SPACING = 10;
    public static final Pos DEFAULT_VBOX_ALIGNMENT = Pos.CENTER;

    // High score
    public static final String HIGH_SCORE_STYLE = "-fx-font-size: "+ DEFAULT_FONT_SIZE*1.25 +"px;";
    public static final Color HIGH_SCORE_COLOR = Color.YELLOW;
    public static final Color HIGH_SCORE_STROKE_COLOR = Color.BLACK;
    public static final double HIGH_SCORE_STROKE_WIDTH = 3.0;

}

