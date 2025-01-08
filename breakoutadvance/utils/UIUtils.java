package breakoutadvance.utils;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.IOException;

public class UIUtils {
    public static Font getFont(String fontName, int fontSize) throws IOException {
        try {
            FileInputStream fontStream = new FileInputStream("assets/fonts/"+ fontName + ".ttf");
            return Font.loadFont(fontStream, fontSize);

        } catch (Exception e) {
            try {
                return new Font(fontName, fontSize);
            } catch (Exception e1) {
                e.printStackTrace();
                System.out.println("Font file not found!");

                e1.printStackTrace();
                System.out.println("Font name not found!");
            }
        }

        return new Font("arial", fontSize);
    }
}
