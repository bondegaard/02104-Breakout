package breakoutadvance.utils;

import breakoutadvance.Breakout;
import breakoutadvance.scenes.menus.MainMenu;
import breakoutadvance.scenes.menus.SettingsMenu;
import breakoutadvance.scenes.PlayScene;

public class SetSceneUtil {
    public static void playScene(int n, int m) {
        Breakout.getInstance().setCurrentScene(new PlayScene(n, m));
    }

    public static void mainMenu() {
        Breakout.getInstance().setCurrentScene(new MainMenu());
    }

    public static void settingsMenu() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    public static void quitGame() {
        System.exit(0);
    }
}
