package breakoutadvance.levels;

import breakoutadvance.persistentdata.DataManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelManager {

    private final DataManager dataManager;

    private final List <Level> levels = new ArrayList<>();

    public LevelManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    public void load() {
        String directoryPath = "assets/levels";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        // Only have json files
        File[] files = directory.listFiles();
        assert files != null;
        files = Arrays.stream(files).filter(file -> file.getName().endsWith(".json")).toArray(File[]::new);

        for (File file : files) {
            if (!file.exists()) continue;

            try (FileReader fileReader = new FileReader(file)) {
                Level level = dataManager.getGson().fromJson(fileReader, Level.class);
                if (level == null) continue;

                levels.add(level);
            } catch (IOException e) {
                System.err.println("Error reading data file: " + e.getMessage());
            }
        }

        System.out.println("Levels loaded: " + levels.size());
    }

    public Level getLevelByName(String name) {
        return levels.stream().filter(level -> level.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

