package breakoutadvance.utils;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum Fonts {
    BLACEB(Constants.FONT_FILEPATH + "BLACEB__.TTF"),
    OPENSANS(Constants.FONT_FILEPATH + "OpenSans-Regular.ttf");

    private final String filename;
    private static final ConcurrentHashMap<String, Font> fonts = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(Fonts.class.getName());

    Fonts(String filename) {
        this.filename = filename;
    }

    public static void load() {
        for (Fonts font : Fonts.values()) {
            CompletableFuture.runAsync(() -> {
                try {
                    loadFont(font.filename, 64); // Default size
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Failed to load font: " + font.filename, e);
                }
            });
        }
    }

    public static void loadFont(String filePath, double size) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Font font = Font.loadFont(fileInputStream, size);
            fonts.put(filePath, font);
        } catch (IOException | IllegalStateException e) {
            System.err.println("Error loading found: " + filePath + " " + e.getMessage());
        }
    }

    public static Font getFont(String filePath, double size) {
        // If the font is not loaded, load it on demand
        return fonts.computeIfAbsent(filePath, path -> {
            try {
                loadFont(path, size);
                return fonts.get(path);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load font on demand: " + path, e);
                return null; // Return null if font cannot be loaded
            }
        });
    }

    public static Font getFont(String filePath) {
        return getFont(filePath, 64); // Default size
    }
}
