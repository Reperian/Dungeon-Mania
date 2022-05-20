package dungeonmania.dungeon.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.*;
import java.io.IOException;

import dungeonmania.entities.Entity;
import dungeonmania.entities.player.Player;
import dungeonmania.goals.Goal;
import dungeonmania.util.FileLoader;

public class DungeonLoader {
    private int width;
    private int height;
    private String gameMode;
    private String id;
    private String name;
    private Goal goal;
    private List<Entity> entities = new ArrayList<>();
    /**
     * Initial setup for the game before anything else is loaded
     * @param gameMode
     */
    public static void setupGameMode(String gameMode) {
        switch (gameMode) {
            case "Standard":
                Player.setFullHealth(20);
                break;
            case "Hard":
                Player.setFullHealth(15);
                break;
            case "Peaceful":
                Player.setFullHealth(20);
                break;
        }
    }

    /**
     * @precondition valid file path is provided, The format of the json must be in:
     * {
     *  "entities" : []
     *  "goal-condition" : {}
     * }
     * Also it should be guarenteed that a player is within the entity list
     * @postcondition All fields of dungeonloader will not be null
     * @param fullPath
     * @return
     */

    public static DungeonLoader loadDungeon(String fullPath, String gameMode) throws IllegalArgumentException {
        try {
            // Loads the file as a string
            String data = FileLoader.loadResourceFile(fullPath);
            // Parse string to create Json object
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            return loadDungeon(jsonObject, gameMode);
        }
        catch(IOException exception) {
            throw new IllegalArgumentException("File in invalid format");
        }
    }

    public static DungeonLoader loadDungeon(JsonObject data, String gameMode) throws IllegalArgumentException {
        // Setup gson
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = builder.create();
        // Make instance of dungeon loader and gets each individual fields
        DungeonLoader dungeonLoader = new DungeonLoader();
        // Gamemode will be null if the game is loaded
        dungeonLoader.gameMode = gameMode != null ? gameMode : gson.fromJson(data.get("game_mode"), String.class);
        setupGameMode(dungeonLoader.gameMode);
        // Gets all other fields
        dungeonLoader.id = gson.fromJson(data.get("id"), String.class);
        dungeonLoader.name = gson.fromJson(data.get("name"), String.class);
        // Keep track of height and width of the map
        int maxHeight = 0;
        int maxWidth = 0;
        // Get all the entities loaded
        JsonArray entities = gson.fromJson(data.get("entities"), JsonArray.class);
        for (int i = 0; i < entities.size(); i++) {
            JsonObject ent = entities.get(i).getAsJsonObject();
            maxWidth = maxWidth < ent.get("x").getAsInt() ? ent.get("x").getAsInt() : maxWidth;
            maxHeight = maxHeight < ent.get("y").getAsInt() ? ent.get("y").getAsInt() : maxHeight;
            dungeonLoader.entities.add(EntityLoader.getEntity(ent, dungeonLoader.gameMode));
        }
        // Sets height and width
        dungeonLoader.width = maxWidth + 1;
        dungeonLoader.height = maxHeight + 1;
        // Load goals
        JsonObject goal = gson.fromJson(data.get("goal-condition"), JsonObject.class);
        dungeonLoader.goal = GoalLoader.loadGoal(null, goal.get("goal"), goal.get("subgoals"));
        return dungeonLoader;
    }

    /**
     * Loads a save file
     * @param fullPath
     * @return DungeonLoader
     */
    public static DungeonLoader loadDungeon(String fullPath) {
        return loadDungeon(fullPath, null);
    }

    /**
     * @return int return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return int return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return int return the height
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * @return List<Entity> return the entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * @return int return the ID
     */
    public String getID() {
        return id;
    }

    /**
     * @return int return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return goal return the Goal
     */
    public Goal getGoal() {
        return goal;
    }
    
    /**
     * @return goal return the Goal
     */
    public Goal getGoalJsonObject() {
        return goal;
    }
}
