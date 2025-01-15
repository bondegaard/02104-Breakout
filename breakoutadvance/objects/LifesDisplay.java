package breakoutadvance.objects;

import breakoutadvance.scenes.PlayScene;
import breakoutadvance.utils.Constants;
import breakoutadvance.utils.Images;
import breakoutadvance.utils.WindowUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class LifesDisplay {

    private final List<ImageView> lifeImages = new ArrayList<>();

    private final Image fullHeartImg;
    private final Image emptyHeartImg;


    public LifesDisplay() {
        this.fullHeartImg = Images.getImage(Constants.IMAGE_PATH + "heart_full.png");
        this.emptyHeartImg = Images.getImage(Constants.IMAGE_PATH + "heart_empty.png");
    }


    public void updateLives(PlayScene playScene, int lives) {
        // Clear existing life images
        lifeImages.forEach(image -> playScene.getPane().getChildren().remove(image));
        lifeImages.clear();

        // Add full heart images for the number of lives remaining
        for (int i = 0; i < lives; i++) {
            ImageView fullHeart = new ImageView(fullHeartImg);
            fullHeart.setFitWidth(32);
            fullHeart.setFitHeight(32);
            fullHeart.setX(WindowUtils.getWindowWidth() - (i + 1) * 48); // Adjust the X position for each heart
            fullHeart.setY(WindowUtils.getWindowHeight() - 80); // Adjust the Y position for each heart
            lifeImages.add(fullHeart);
        }

        // Add empty heart images for the remaining slots up to 3
        for (int i = lives; i < 3; i++) {
            ImageView emptyHeart = new ImageView(emptyHeartImg);
            emptyHeart.setFitWidth(32);
            emptyHeart.setFitHeight(32);
            emptyHeart.setX(WindowUtils.getWindowWidth() - (i + 1) * 48); // Adjust the X position for each heart
            emptyHeart.setY(WindowUtils.getWindowHeight() - 80); // Adjust the Y position for each heart
            lifeImages.add(emptyHeart);
        }

        // Add the life images to the scene (assuming you have a method to get the pane)
        for (ImageView heart : lifeImages) {
            playScene.getPane().getChildren().add(heart);
        }
    }
}
