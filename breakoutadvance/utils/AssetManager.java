package breakoutadvance.utils;

import breakoutadvance.Breakout;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * A singleton AssetManager that loads and stores images (and possibly other assets)
 * so they can be reused throughout the application without repeatedly loading from disk.
 */
public class AssetManager {

    // The singleton instance
    private static AssetManager instance;

    // In-memory storage for all your images
    private static final Map<String, Image> images = new HashMap<>();

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
     * PreLoad all the image files asynchronously
     */
    public static void load() {
        final String BASEPATH = "./assets/img/";

        // Run async so it doesn't block the main thread
        CompletableFuture.runAsync(() -> {
            try {
                List<String> filesList = getAllPNGFiles(BASEPATH);

                for (String filePath : filesList) {
                    putImage(filePath);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            });
    }



    public static List<String> getAllPNGFiles(String directoryPath) throws IOException {
        List<String> pngFileList = new ArrayList<>();

        try {
            Files.walk(Paths.get(directoryPath)) // Recursively walk through the directory
                    .filter(Files::isRegularFile)    // Filter only regular files
                    .filter(path -> path.toString().toLowerCase().endsWith(".png")) // Check for .png extension
                    .forEach(path -> pngFileList.add(path.toString())); // Add file paths to the list
        } catch (IOException e) {
            throw new IOException("Error while reading files", e);
        }

        return pngFileList;
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
     * @param url the url to image
     */
    public static void putImage(String url) {
        String basePath = "./assets/img/";

        try {
            // Remove the starting path, slashes, and .png
            String key = url.replaceFirst("^" + ".", "") // Remove the starting path
                    .replace("\\assets\\img\\", "")
                    .replace("\\", "-") // Replace slashes with dashes
                    .replace(".png", "");// Remove .png extension
            // Load the image
            Image image = new Image(url);
            // Put the key-image pair in the map
            images.put(key, image);
            System.out.printf("Saved image with key %s \n", key);
        } catch (Exception e) {
            System.err.println("Error putting image with key derived from URL: " + url + " | " + e.getMessage());
        }
    }


}