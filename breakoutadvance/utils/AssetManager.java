package breakoutadvance.utils;

import breakoutadvance.Breakout;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton AssetManager that loads and stores images (and possibly other assets)
 * so they can be reused throughout the application without repeatedly loading from disk.
 */
public class AssetManager {

    // The singleton instance
    private static AssetManager instance;

    // In-memory storage for all your images
    private final Map<String, Image> images = new HashMap<>();

    /**
     * Private constructor to prevent external instantiation.
     */
    private AssetManager() {
    }

    /**
     * Get the singleton instance.
     */
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    /**
     * Preloads all necessary images into our Map.
     */
    public void loadAllImages() {
        loadPaddleImages(Breakout.getInstance().getDataManager().getData().getPaddleColor());
        loadBallImages(Breakout.getInstance().getDataManager().getData().getBallColor());

        // load other assets, like blocks, backgrounds, etc.
    }

    /**
     * Reloads all images as calls loadAllImages.
     */
    public void reloadAllImages() {
        images.clear();
        loadAllImages();
    }

    /**
     * Utility method to load a single set of paddle images (left, middle, right).
     *
     * @param color e.g., "blue", "red", "green", etc.
     */
    private void loadPaddleImages(String color) {
        String basePath = "./assets/img/OpenGameArt/paddles/";

        // The loaded images are stored as {color} + Left/Middle/Right fx redLeft
        putImage(color + "Left",  basePath + color + "Left.png");
        putImage(color + "Middle", basePath + color + "Middle.png");
        putImage(color + "Right",  basePath + color + "Right.png");
    }

    /**
     * Utility method to load a single ball image.
     *
     * @param color e.g., "blue", "red", "green", etc.
     */
    private void loadBallImages(String color) {
        String basePath = "./assets/img/OpenGameArt/balls/";

        // The loaded images are stored as {color} + Ball fx redBall
        putImage(color + "Ball", basePath + color + ".png");
    }

    /**
     * Retrieve an image by its key.
     *
     * @param key the key to retrieve (e.g. "blueLeft")
     * @return the corresponding Image or null if not found
     */
    public Image getImage(String key) {
        return images.get(key);
    }

    /**
     * Put an image into the map with a try-catch block.
     *
     * @param key the key to store the image under
     * @param url the url to image
     */
    public void putImage(String key, String url) {
        try {
            Image image = new Image(url);
            images.put(key, image);
        } catch (Exception e) {
            System.err.println("Error putting image with key: " + key + " | "+ e.getMessage());
        }
    }
}