package breakoutadvance.utils;

import breakoutadvance.Breakout;
import breakoutadvance.UI.menus.MainMenu;
import breakoutadvance.UI.menus.SettingsMenu;
import breakoutadvance.scenes.PlayScene;

public class SetSceneUtil {
    public void playScene(int n, int m) {
        Breakout.getInstance().setCurrentScene(new PlayScene(n, m));
    }

    public void mainMenu() {
        Breakout.getInstance().setCurrentScene(new MainMenu());
    }

    public void settingsMenu() {
        Breakout.getInstance().setCurrentScene(new SettingsMenu());
    }

    public void quitGame() {
        System.exit(0);
    }
}
