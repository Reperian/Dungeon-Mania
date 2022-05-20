package dungeonmania.entities.moving_entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.util.InventorySaver;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Inventory;
import dungeonmania.movement.MovementStrategy;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity implements Inventory{
    private int health;
    private int damage;
    private List<Collectable> inventory = new ArrayList<>();
    private Direction moved = Direction.NONE;
    protected MovementStrategy movement;
    protected MovementStrategy defaultMovement;
    protected MovingEntity(String type, Position position) {
        super(type, position);
    }
    
    /**
     * @return int return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
        if (getHealth() <= 0) {
            onDeath();
        }
    }
    
    /**
     * @return int return the damage dealt by this entity
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    /**
     * @return List<Collectable> return the inventory
     */
    @Override
    public List<Collectable> getInventory() {
        return inventory;
    }
    
    /**
     * @param inventory the inventory to set
     */
    public void setInventory(List<Collectable> inventory) {
        this.inventory = inventory;
    }
    /**
     * The action performed on death of this entity.
     * By default it will drop all items in inventory on its death location
     */
    public void onDeath() {
        Dungeon.getInstance().getPlayer().getInventory().addAll(inventory);
        Dungeon.getInstance().getEntities().remove(this);
    }

    public float damageMultiplier() {
        return 1;
    }

    public MovementStrategy getMovementStrategy() {
        return movement;
    }

    public void setMovementStrategy(MovementStrategy movement) {
        this.movement = movement;
    }
    
    public MovementStrategy getDefaultMovementStrategy() {
        return defaultMovement;
    }
    @Override
    public JsonObject toJsonObject(){
        JsonObject obj = super.toJsonObject();
        obj.add("inventory", InventorySaver.inventoryToJsonArray(inventory));
        return obj;
    }
    
    @Override
    public List<AnimationQueue> getAnimationResponse() {
        if (moved == Direction.RIGHT) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("translate-x -1", "translate-x 1, over 0.5s"), false, -1));
        }
        if (moved == Direction.DOWN) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("translate-y -1", "translate-y 1, over 0.5s"), false, -1));
        }
        if (moved == Direction.LEFT) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("translate-x 1", "translate-x -1, over 0.5s"), false, -1));
        }
        if (moved == Direction.UP) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("translate-y 1", "translate-y -1, over 0.5s"), false, -1));
        }
        return super.getAnimationResponse();
    }

    public void setMoved(Direction direction) {
        this.moved = direction;
    }

    public Direction getMoved() {
        return moved;
    }
}
