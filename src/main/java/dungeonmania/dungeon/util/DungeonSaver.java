package dungeonmania.dungeon.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.util.FileLoader;

public class DungeonSaver {

    public static String getId(String name) {
        String[] tokenisedString = name.split("-");
        return tokenisedString[tokenisedString.length - 1];
    }

    /**
     * @precondition the name must be unique. The reference map for this dungeon must exist.
     * @postcondition the dungeon json will be in the same format as json map files but with additional fields
     * @param name
     */
    public static void saveDungeon(String name) {
        Dungeon dungeon = Dungeon.getInstance();
        // create a gson type with specified properties
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = builder.create();
        // get a hashmap of all entities in json form
        JsonArray entities = new JsonArray();
        dungeon.getEntities().stream().forEach(e -> entities.add(e.toJsonObject()));
        // create a map containing all attributes of a dungeon then add those attributes to the map
        Map<String, Object> dungeonJsonMap = new HashMap<>();
        dungeonJsonMap.put("id", getId(name));
        dungeonJsonMap.put("name", dungeon.getDungeonName());
        dungeonJsonMap.put("entities", entities);
        dungeonJsonMap.put("game_mode", dungeon.getGameMode());
        // gets goal from reference map, no exception should be thrown as from the precondition
        dungeonJsonMap.put("goal-condition", dungeon.getGoal().toJsonObject());

        // Get the resultant json string
        String outputString = gson.toJson(dungeonJsonMap, dungeonJsonMap.getClass());
        try{
            // Creates a json file in the saves folder containing the json string
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(Path.of(FileLoader.class.getResource("/saves").toURI()).toString() +
                                    "/" + name + ".json"),
                                    "utf-8"));
            writer.write(outputString);
            writer.close();
        } catch (IOException | URISyntaxException message) {
        }
    }
}
