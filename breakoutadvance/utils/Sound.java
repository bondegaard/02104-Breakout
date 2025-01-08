package breakoutadvance.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public enum Sound {

    HIT_1("assets/sound/hit1.wav"),
    HIT_2("assets/sound/hit2.wav"),
    HIT_3("assets/sound/hit3.wav"),
    PADDLE("assets/sound/paddle.wav"),
    WON("assets/sound/won.wav"),
    LOSE("assets/sound/lose.wav");

    private final String fileName;
    private static final Map<Sound, Media> mediaMap = new HashMap<>();
    private static MediaPlayer mediaPlayer;

    Sound(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public static Sound getRandomHitSound() {
        return Sound.values()[(int) (Math.random() * 3)];
    }

    public static void playSound(Sound sound) {
        Media media = mediaMap.get(sound);
        if (media != null) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    public static void load() {
        for (Sound sound : Sound.values()) {
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