package breakoutadvance.persistentdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    private Gson gson;


    public void load() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String directoryPath = "data"; // Removed leading slash for relative path

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File dataFile = new File(directory, "data.json");

        if (!dataFile.exists()) {
            try (FileWriter fileWriter = new FileWriter(dataFile)) {
                gson.toJson(createDefaultData(), fileWriter);
            } catch (IOException e) {
                System.err.println("Error creating data file: " + e.getMessage());
            }
        }
    }

    private JsonObject createDefaultData() {
        JsonObject basicData = new JsonObject();
        basicData.addProperty("highscore", 0);
        basicData.addProperty("volume", 0.5);
        basicData.addProperty("mute", false);
        basicData.add("previous-games", new JsonArray());
        return basicData;
    }
}
