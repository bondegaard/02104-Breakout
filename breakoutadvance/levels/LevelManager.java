package breakoutadvance.levels;

import breakoutadvance.persistentdata.DataManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LevelManager class is used to manage the level
 * Includes functions such as loading all levels, and choosing the next level
 */
public class LevelManager {

    private final DataManager dataManager; // Data manager

    private final List<Level> levels = new ArrayList<>(); // List of levels

    private Level currentLevel; // Current level

    /**
     * Setting the dataManager
     *
     * @param dataManager dataManager
     */
    public LevelManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Loading all levels, and adding them to levels list
     */
    public void load() {
        // Setting the path
        String directoryPath = "assets/levels";

        // Checking if the directory exists, if not it's created
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Only have json files
        File[] files = directory.listFiles();
        assert files != null;
        files = Arrays.stream(files).filter(file -> file.getName().endsWith(".json")).toArray(File[]::new);

        // Running through the files
        for (File file : files) {
            // Don't check if the file doesn't exist
            if (!file.exists()) continue;

            try (FileReader fileReader = new FileReader(file)) {
                // Getting a level configuration from a json-file
                Level level = dataManager.getGson().fromJson(fileReader, Level.class);
                if (level == null) continue;

                // Adding the level to levels list
                levels.add(level);
            } catch (IOException e) {
                System.err.println("Error reading data file: " + e.getMessage());
            }
        }

        System.out.println("Levels loaded: " + levels.size());

        // Finding and setting the first level
        this.currentLevel = getLevelByName("level_1");
    }

    public Level getLevelByName(String name) {
        // Returning the level, based on the given String name
        return levels.stream().filter(level -> level.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setNextLevel() {
        // Setting the next level, based on 'nextLevel' data in the current level file
        Level nextLevel = levels.stream().filter(level -> level.getName().equalsIgnoreCase(currentLevel.getNextLevel())).findFirst().orElse(null);

        // If nextLevel doesn't exist, choose a random level
        if (nextLevel == null) {
            currentLevel = levels.get((int) (Math.random() * levels.size()));
            return;
        }

        // If nextLevel does exist, update the level
        currentLevel = nextLevel;
    }
}

