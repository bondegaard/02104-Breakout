package breakoutadvance.utils.resources;

import breakoutadvance.Breakout;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Enum to store all the sounds used in the game
 */
public enum Sound {

    // Sound files
    HIT_1("./assets/sound/hit1.wav"),
    HIT_2("./assets/sound/hit2.wav"),
    HIT_3("./assets/sound/hit3.wav"),
    PADDLE("./assets/sound/paddle.wav"),
    WON("./assets/sound/won.wav"),
    LOSE("./assets/sound/lose.wav");

    // File name of the sound
    private final String fileName;

    // Map to store the media objects
    private static final Map<Sound, Media> mediaMap = new HashMap<>();

    // MediaPlayer object to play the sound
    private static MediaPlayer mediaPlayer;

    /**
     * Constructor for the Sound enum
     * @param fileName The file name of the sound
     */
    Sound(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * Get a random hit sound
     * @return A random hit sound
     */
    public static Sound getRandomHitSound() {
        return Sound.values()[(int) (Math.random() * 3)];
    }

    /**
     * Play a sound
     * @param sound The sound to play
     */
    public static void playSound(Sound sound) {
        if (Breakout.getInstance().getDataManager().getData().isMute()) return;
        Media media = mediaMap.get(sound);
        if (media != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(Breakout.getInstance().getDataManager().getData().getVolume());
            mediaPlayer.play();
        }
    }

    /**
     * PreLoad all the sound files asynchronously
     */
    public static void load() {
        for (Sound sound : Sound.values()) {

            // Run async so it doesn't block the main thread
            CompletableFuture.runAsync(() -> {
                try {
                    Media media = new Media(new File(sound.getFileName()).toURI().toString());
                    mediaMap.put(sound, media);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error loading sound file: " + sound.getFileName());
                }
            });
        }
    }
}