package breakoutadvance.utils;

import java.util.HashMap;
import java.util.Map;

public enum Image {

    // Background files
    BACKGROUND_1( Constants.BACKGROUND_FILEPATH + "background.jpg"),
    BACKGROUND_2("./assets/img/backgrounds/background.png"),
    BACKGROUND_3("./assets/img/backgrounds/background2.png"),
    BACKGROUND_4("./assets/img/backgrounds/background2Færdig.png"),
    BACKGROUND_5("./assets/img/backgrounds/background2Færdig2.png"),

    // Ball files
    BALL_BEIGE("./assets/img/OpenGameArt/balls/beige.png"),
    BALL_BLACK("./assets/img/OpenGameArt/balls/black.png"),
    BALL_BLUE("./assets/img/OpenGameArt/balls/blue.png"),
    BALL_BLUE2("./assets/img/OpenGameArt/balls/blue2.png"),
    BALL_BROWN("./assets/img/OpenGameArt/balls/brown.png"),
    BALL_GREEN("./assets/img/OpenGameArt/balls/green.png"),
    BALL_PINK("./assets/img/OpenGameArt/balls/pink.png"),
    BALL_RED("./assets/img/OpenGameArt/balls/red.png"),;


    // Paddle files




    // Powerup files

    // File name of image
    private final String filename;

    // Map to store the image objects
    private static final Map<String, Image> images = new HashMap<String, Image>();

    Image(String filename) {
        this.filename = filename;
    }
}
