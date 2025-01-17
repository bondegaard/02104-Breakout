package breakoutadvance.utils;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enum used to setting font paths, and loading these
 */
public enum Fonts {
    // Fonts
    BLACEB(Constants.FONT_FILEPATH + "BLACEB__.ttf"),
    BLACKWOOD(Constants.FONT_FILEPATH + "BlackwoodCastle.ttf");

    private static final ConcurrentHashMap<String, Font> fonts = new ConcurrentHashMap<>(); // String and fonts
    private static final Logger logger = Logger.getLogger(Fonts.class.getName()); // Logger
    private final String filename; // File name

    Fonts(String filename) {
        this.filename = filename;
    }

    /**
     * Method to run through all fonts, and call loadFont() for each of them
     */
    public static void load() {
        for (Fonts font : Fonts.values()) {
            try {
                loadFont(font.filename, 64); // Default size
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load font: " + font.filename, e);
            }
        }
    }

    /**
     * Method to load fonts
     *
     * @param filePath file path
     * @param size size
     * @throws IOException if unable to load font
     */
    public static void loadFont(String filePath, double size) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Font font = Font.loadFont(fileInputStream, size);
            fonts.put(filePath, font);
        } catch (IOException | IllegalStateException e) {
            System.err.println("Error loading found: " + filePath + " " + e.getMessage());
        }
    }

    /**
     * Getting font, to be able to use it throughout the program
     *
     * @param filePath filepath
     * @param size size
     * @return Font
     */
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
