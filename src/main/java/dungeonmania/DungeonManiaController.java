package dungeonmania;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.map_generation.PrimsAlgorithm;
import dungeonmania.dungeon.util.DungeonSaver;
import dungeonmania.entities.player.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DungeonManiaController {


    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    /**
     * Creates a new game
     * @param dungeonName
     * @param gameMode
     * @return
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        gameMode = gameMode.substring(0, 1).toUpperCase() + gameMode.substring(1).toLowerCase();
        return newGame(dungeonName, gameMode, System.currentTimeMillis());
    }

    public DungeonResponse newGame(String dungeonName, String gameMode, long seed) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName);
        }
        else if (!getGameModes().contains(gameMode)) {
            throw new IllegalArgumentException(gameMode);
        }
        Dungeon.newDungeon(dungeonName, gameMode, seed);
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Generates a dungeon using prims algorithm
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @param gameMode
     * @return
     * @throws IllegalArgumentException
     */
    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode) throws IllegalArgumentException {
        gameMode = gameMode.substring(0, 1).toUpperCase() + gameMode.substring(1).toLowerCase();
        // data stores all neccessary info to load map
        JsonObject data = new JsonObject();
        PrimsAlgorithm algorithm = new PrimsAlgorithm(xStart, yStart, xEnd, yEnd);
        algorithm.runAlgorithm();
        // Store the rest of the fields into data
        JsonArray entities = algorithm.mapToJsonArray();
        entities.add(new Player(new Position(xStart, yStart)).toJsonObject());
        data.add("entities", entities);
        JsonObject goal = new JsonObject();
        goal.addProperty("goal", "exit");
        data.add("goal-condition", goal);
        // Load this dungeon
        Dungeon.loadDungeon(data, gameMode);
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Saves the instance of a dungeon
     * @precondition name must be unique and never used before
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        DungeonSaver.saveDungeon(name);
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Loads up a save file
     * @precondition name must be exactly the same as shown from allGames()
     * @param name
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        Dungeon.loadDungeon(name);
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Returns all savefiles
     * @return List<String>
     */
    public List<String> allGames() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/saves");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Updates the dungeon by one tick
     * @param itemUsed
     * @param movementDirection
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        Dungeon.getInstance().onTick(itemUsed, movementDirection);
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Player interaction with an entity via its id
     * @param entityId
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        if(!Dungeon.getInstance().interact(entityId)) {
            throw new InvalidActionException("Interaction with the given entity is invalid");
        }
        
        return Dungeon.getInstance().getDungeonResponse();
    }
    /**
     * Builds an item
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Dungeon.getInstance().build(buildable);
        return Dungeon.getInstance().getDungeonResponse();
    }
}