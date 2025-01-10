package breakoutadvance.utils;

import javafx.scene.text.Font;

import java.io.FileInputStream;

public class FontUtil {
    /**
     * Loads the font, if any errors it falls back to a default font Arial.
     */
    public static Font getFont() {
        try (FileInputStream fontStream = new FileInputStream("./assets/fonts/BLACEB__.TTF")) {
            return Font.loadFont(fontStream, 64);
        } catch (Exception e) {
            System.err.println("Font file not found! Fallback to Arial.");
            return Font.font("Arial", 64);
        }
    }
}
