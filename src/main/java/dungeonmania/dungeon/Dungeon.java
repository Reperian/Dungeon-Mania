package dungeonmania.dungeon;

import java.time.Instant;
import java.util.*;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.util.BuildableCreator;
import dungeonmania.dungeon.util.DungeonLoader;
import dungeonmania.entities.*;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.moving_entities.spawn.MovingEntitySpawner;
import dungeonmania.entities.behaviours.Interactable;
import dungeonmania.entities.behaviours.Usable;
import dungeonmania.goals.*;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.player.battle_strategies.AvoidBattleStrategy;
import dungeonmania.entities.player.battle_strategies.StandardStrategy;
import dungeonmania.entities.player.status_states.DefaultState;
import dungeonmania.entities.player.status_states.InvisibleState;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private static Dungeon dungeon = null;

    private final int INTERACT_RADIUS = 2;

    private int height;
    private int width;
    private String gameMode;
    private String dungeonName;
    private String dungeonId;
    private Goal goal;
    private String goals;
    private List<Entity> entities;
    private Player player;
    private Random random;
    private int tick;
    private Position entry;
    
    /**
     * Constructor for the dungeon
     * @param dl
     * @param dungeonId
     * @param dungeonName
     */
    private Dungeon(DungeonLoader dl, String dungeonId, String dungeonName) {
        this.height = dl.getHeight();
        this.width = dl.getWidth();
        this.entities = dl.getEntities();
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.gameMode = dl.getGameMode();
        this.player = EntityUtils.getEntitiesToTypeList(entities, Player.class).get(0);
        this.random = new Random(System.currentTimeMillis());
        this.goal = dl.getGoal();
        this.entry = player.getPosition();
        this.tick = 0;
        dungeon = this;
    }

    /**
     * Constructor for the dungeon
     * @param dl
     */
    private Dungeon(DungeonLoader dl) {
        this(dl, dl.getID(), dl.getName());
    }

    /**
     * @precondition path must be valid
     * Loads up a new dungeon instance
     * @return List<EntityResponse>
     */
    public static Dungeon newDungeon(String path, String gameMode, long seed) {
        Dungeon dungeon = new Dungeon(DungeonLoader.loadDungeon("/dungeons/"+path+".json", gameMode), String.valueOf(Instant.now().toEpochMilli()), path);
        dungeon.random = new Random(seed);
        postLoad(dungeon);
        return dungeon;
    }

    /**
     * @precondition path must be valid
     * Loads up a new dungeon instance
     * @return List<EntityResponse>
     */
    public static Dungeon loadDungeon(String path) {
        Dungeon dungeon = new Dungeon(DungeonLoader.loadDungeon("/saves/"+path+".json"));
        postLoad(dungeon);
        return dungeon;
    }
    /**
     * @precondition path must be valid
     * Loads up a new dungeon instance
     * @return List<EntityResponse>
     */
    public static Dungeon loadDungeon(JsonObject data, String gameMode) {
        Dungeon dungeon = new Dungeon(DungeonLoader.loadDungeon(data, gameMode));
        dungeon.dungeonId = String.valueOf(Instant.now().toEpochMilli());
        dungeon.dungeonName = "RandomMaze";
        postLoad(dungeon);
        return dungeon;
    }
    /**
     * post setup after loading the map
     */
    private static void postLoad(Dungeon dungeon) {
        dungeon.entities.forEach(Entity::init);
        dungeon.goals = dungeon.goal.checkGoal();

        switch (dungeon.getGameMode()) {
            case "Standard":
                Player.setDefaultBattleStrategy(new StandardStrategy());
                Player.setDefaultStatus(new DefaultState());
                break;
            case "Hard":
                Player.setDefaultBattleStrategy(new StandardStrategy());
                Player.setDefaultStatus(new DefaultState());
                break;
            case "Peaceful":
                Player.setDefaultBattleStrategy(new AvoidBattleStrategy());
                Player.setDefaultStatus(new InvisibleState());
                break;
        }
        dungeon.player.setBattleStrategy(Player.getDefaultBattleStrategy());
        dungeon.player.setStatus(Player.getDefaultStatus());
    }

    /**
     * Updates the dungeon by a single tick
     * @precondition newGame or loadDungeon must be called at least once before calling this function
     * @param itemUsed
     * @param movementDirection
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public void onTick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        tick++;
        use(itemUsed);
        player.move(movementDirection);
        for (Entity entity : new ArrayList<>(entities)) {
            entity.onTick();
        }
        MovingEntitySpawner.Spawn(gameMode, tick);
        goals = goal.checkGoal();
    }

    /**
     * @precondition newGame or loadDungeon must be called at least once before calling this function
     * @param entityId
     * @return
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public boolean interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        
        Entity entity = EntityUtils.getEntityFromId(entityId);
        
        if (!(entity instanceof Interactable)) {
            throw new IllegalArgumentException("Cannot interact with the given entity");
        } else if (!EntityUtils.isWithinRadius(entity.getPosition(), getPlayer().getPosition(), INTERACT_RADIUS)) {
            throw new InvalidActionException("Entity is not within the interact radius of the player");
        }
        
        Interactable iEntity = (Interactable) entity;
        boolean interactable = iEntity.interact();
        goals = goal.checkGoal();
        
        return interactable;
    }

    /**
     * @precondition newGame or loadDungeon must be called at least once before calling this function
     * @param buildable
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public void build(String buildable) throws IllegalArgumentException, InvalidActionException {
        
        Collectable builtEntity = BuildableCreator.BuildableFactory(buildable);
        player.addItemToInventory(builtEntity);
        
    }

    public void use(String id) throws IllegalArgumentException, InvalidActionException {
        Entity item = EntityUtils.getEntityFromInventoryId(player.getInventory(), id);
        if (item == null) {
            return;
        }
        if (!(item instanceof Usable)) {
            throw new IllegalArgumentException("No entity is clicked");
        }
        ((Usable) item).use();
    }
    
    /**
     * @precondition newGame or loadDungeon must be called at least once before calling this function
     * @postcondition the current instance of the dungeon
     * @return
     */
    public static Dungeon getInstance() {
        return dungeon;
    }

    /**
     * Gets the dungeon response for this dungeon
     * @return DungeonResponse
     */
    public DungeonResponse getDungeonResponse() {
        return new DungeonResponse(dungeonId, dungeonName, getEntityResponse(), getItemResponse(), BuildableCreator.generateBuildables(), goals, getAnimationResponse());
    }

    /**
     * Gets the Animation response for all entities
     * @return List<ItemResponse>
     */
    private List<AnimationQueue> getAnimationResponse() {
        List<AnimationQueue> resp = new ArrayList<>();
        entities.stream().forEach(e -> resp.addAll(e.getAnimationResponse()));
        return resp;
    }
    /**
     * Gets the item response for the player's inventory
     * @return List<ItemResponse>
     */
    private List<ItemResponse> getItemResponse() {
        List<ItemResponse> resp = new ArrayList<ItemResponse>();
        player.getInventory().stream().forEach(e->resp.add(e.getItemResponse()));
        return resp;
    }

    /**
     * Gets the EntityResponse for all entities in the dungeon
     * @return List<EntityResponse>
     */
    private List<EntityResponse> getEntityResponse() {
        List<EntityResponse> responses = new ArrayList<>();
        entities.forEach(e -> responses.add(e.getEntityResponse()));
        return responses;
    }

    /**
     * @return int return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return int return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return String return the dungeonName
     */
    public String getDungeonName() {
        return dungeonName;
    }

    /**
     * @return String return the dungeonId
     */
    public String getDungeonId() {
        return dungeonId;
    }

    /**
     * @return String return the gameMode
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
     * removes entity from the dungeon
     * @param e
     */
    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    /**
     * adds entity to the dungeon
     * @param e
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * @precondition the dungeon must have an instance
     * returns the player
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }
    /**
     * @precondition the dungeon must have an instance
     * returns the random
     * @return Random
     */
    public Random getRandom() {
        return random;
    }
    /**
     * @precondition the dungeon must have an instance
     * returns the goal
     * @return goal
     */
    public Goal getGoal() {
        return goal;
    }

    public Position getEntry() {
        return entry;
    }

    /**
     * returns the player's inventory
     * @return inventory
     */
    public List<Collectable> getInventory() {
        return getPlayer().getInventory();
        
    }
}
