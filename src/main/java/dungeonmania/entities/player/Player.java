package dungeonmania.entities.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.EntityDamage;
import dungeonmania.entities.behaviours.EntityHealth;
import dungeonmania.entities.behaviours.Inventory;
import dungeonmania.entities.key.Key;
import dungeonmania.entities.moving_entities.FriendlyMercenary;
import dungeonmania.entities.moving_entities.Mercenary;
import dungeonmania.entities.player.battle_strategies.BattleStrategy;
import dungeonmania.entities.player.status_states.StatusState;
import dungeonmania.entities.the_one_ring.TheOneRing;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.util.InventorySaver;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity implements Inventory, EntityHealth, EntityDamage {
    private static final String TYPE = "player";
    private static int FULL_HEALTH = 20; //subject to change
    private static StatusState defaultStatus;
    private static BattleStrategy defaultBattleStrategy;

    private int health;
    private int damage = 1;
    private List<Collectable> inventory = new ArrayList<Collectable>();
    private List<FriendlyMercenary> friendlyMercenaries = new ArrayList<>();
    private boolean battle = false;

    private BattleStrategy battleStrategy;

    private StatusState status;

    public Player(Integer health, Position position) {
        super(TYPE, new Position(position.getX(), position.getY(), 50));
        this.health = health != null ? health.intValue() : FULL_HEALTH;
    }

    public Player(Position position) {
        this(FULL_HEALTH, position);
    }

    /**
     * Moves the player to a new position according to direction
     * @param direction
     */
    public void move(Direction direction) {
        battle = false;
        final Position p = getPosition().translateBy(direction);
        if (checkMove(p, direction)) {               
            this.setPosition(p);
        }
        else {
            return;
        }      
    }
    /**
     * Checks if player can move in a given direction
     * @param position
     * @param dir
     * @return
     */
    public boolean checkMove(Position position, Direction dir) {
        final Position p = position;
        //gets entity at position player will be moving to
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(p);
        
        boolean condition = true;
        
        for (Entity e : entities) {   
            condition = condition && e.onOverlap(this, dir);
        }
        return condition;
    }

    /**
     * Performs a battle
     * Battle is calculated using formula :
     * health = health - ((enemy health * enemy damage) / 10) * dmg multiplier from armor or weapons
     * enemy health = enemy health - ((health * damage * dmg multiplier) /5) * dmg multiplier from armor or weapons
     * @param e
     */
    public void battle(Entity e) {
        battleStrategy.battle(e);
        battle = true;
        checkMercRange(Dungeon.getInstance());
    }

    /**
     * checks if player is within battle radius of mercenary
     * if in range, move the the mercenary an extra time
     * @param d
     */
    public void checkMercRange(Dungeon d) {
        //checks battle radius of mercenaries to see if they move an additional time
        for (Entity entity: new ArrayList<>(d.getEntities())) {
            if (entity instanceof Mercenary) {
                Mercenary m = (Mercenary) entity;
                if (m.checkBattleRadius(this) == true) {
                    m.onTick();
                }
            }
        }
        return;
    }
    
    /**
     * check if player is within battle radius of friendly mercenary
     * if in range, return mercenary
     * @param d
     * @return friendly mercenary
     */
    public FriendlyMercenary checkFriendlyMercRange(Dungeon d) {
        for (Entity entity: new ArrayList<>(d.getEntities())) {
            if (entity.getType().equals("mercenary_friendly")) {
                FriendlyMercenary m = (FriendlyMercenary) entity;
                if (m.checkBattleRadius(this) == true) {
                    return m;
                }
            }
        }
        return null;
    }

    /**
     * Removes 'numRemove' amount of objects of type 'type'
     * @precondition There will be at least 'numRemove' objects of type 'type' in the player's inventory
     * @param type
     */
    public void removeNumOfTypeFromInventory(int numRemove, Class<?> type) {
        
        List<Collectable> collectables = EntityUtils.getCollectablesFromInventory(inventory, type);

        for (int i = 0; i < numRemove; i++) {
            inventory.remove(collectables.get(i)); 
        }
    }

    /**
     * @return int return the health
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Effects upon the death of this player
     */
    private void onDeath() {
        // Check for one ring to revive
        List<TheOneRing> tor = EntityUtils.getInventoryToTypeList(inventory, TheOneRing.class);
        if (tor.size() == 0) {
            Dungeon.getInstance().removeEntity(this);
        } else {
            tor.get(0).rarePower();
        }
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        if (health < FULL_HEALTH) {
            this.health = health;
        // Checks if the player is dead
        } 
        else {
            this.health = FULL_HEALTH;
            return;
        }
        if (health <= 0) {
            onDeath();
        }
    }  
    
    /**
     * @param health the value to set full health to
     */
    public static void setFullHealth(int full_health) {
        FULL_HEALTH = full_health;
    }

    /**
     * @return int return full health
     */
    public int getFullHealth() {
        return FULL_HEALTH;
    }

    /**
     * @return List<Collectable> return the inventory
     */
    @Override
    public List<Collectable> getInventory() {
        return inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        status.nextState();
    }

    public void move(Position translateBy) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return true;         
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonObject toJsonObject(){
        JsonObject obj = super.toJsonObject();
        obj.addProperty("health", health);
        obj.add("inventory", InventorySaver.inventoryToJsonArray(inventory));
        return obj;
    }
    
    /**
     * 
     * @param item add the collectable entity to the Inventory
     */
    public void addItemToInventory(Collectable item) {
        if (item.getClass().equals(Key.class)) {
            for (Collectable obj : inventory) {
                if (obj.getClass().equals(Key.class)) {
                    return;
                }
            }
        }
        inventory.add(item);
    }

    /**
     * 
     * @param item add the collectable entity to the Inventory
     */
    public void removeItemFromInventory(Collectable item) {
        inventory.remove(item);
    }
    
    /**
     * 
     * @param sets the Inventory
     */
    public void setInventory(List<Collectable> inventory) {
        this.inventory = inventory;
    }

    /**
     * @return int return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        friendlyMercenaries = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), FriendlyMercenary.class);
        friendlyMercenaries.removeIf(e -> Mercenary.class.isInstance(e));
    }
    /**
     * Returns the allys of this player
     * @return List<FriendlyMercenary>
     */
    public List<FriendlyMercenary> getAllyList() {
        return new ArrayList<>(friendlyMercenaries);
    }
    /**
     * adds an ally for this player
     */
    public void addAlly(FriendlyMercenary ally) {
        friendlyMercenaries.add(ally);
    }
    /**
     * removes an ally for this player
     */
    public void removeAlly(FriendlyMercenary ally) {
        friendlyMercenaries.remove(ally);
    }
    /**
     * gets the BattleStrategy of the player
     * @return BattleStrategy
     */
    public BattleStrategy getBattleStrategy() {
        return battleStrategy;
    }
    /**
     * Gets the status of the player
     * @return StatusState
     */
    public StatusState getStatus() {
        return status;
    }
    /**
     * sets the BattleStrategy of the player
     */
    public void setBattleStrategy(BattleStrategy battleStrategy) {
        this.battleStrategy = battleStrategy;
    }
    /**
     * sets the status of the player
     */
    public void setStatus(StatusState status) {
        this.status = status;
    }

    /**
     * @return List<FriendlyMercenary> return the friendlyMercenaries
     */
    public List<FriendlyMercenary> getFriendlyMercenaries() {
        return friendlyMercenaries;
    }

    /**
     * @param friendlyMercenaries the friendlyMercenaries to set
     */
    public void setFriendlyMercenaries(List<FriendlyMercenary> friendlyMercenaries) {
        this.friendlyMercenaries = friendlyMercenaries;
    }

    /**
     * @return BattleStrategy return the defaultBattleStrategy
     */
    public static BattleStrategy getDefaultBattleStrategy() {
        return defaultBattleStrategy;
    }

    /**
     * @return BattleStrategy return the defaultBattleStrategy
     */
    public static void setDefaultBattleStrategy(BattleStrategy strategy) {
        defaultBattleStrategy = strategy;
    }

    /**
     * @return StatusState return the defaultStatus
     */
    public static StatusState getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * @return StatusState return the defaultStatus
     */
    public static void setDefaultStatus(StatusState state) {
        defaultStatus = state;
    }

    /**
     * Animations for the player
     */
    @Override
    public List<AnimationQueue> getAnimationResponse(){
        animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("healthbar set " + (float) health / FULL_HEALTH, "healthbar tint 0x00ff00"), false, -100));
        if (battle == true) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("healthbar set " + (float) health / FULL_HEALTH, "healthbar tint 0xff0000, over 0.5s","translate-x -0.5", "translate-x 0.5, over 0.2s", "sprite player_battle"), false, -1));
        }
        return super.getAnimationResponse();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float damageMultiplier() {
        return 1;
    }
}
