package dungeonmania.entities.sword;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Sword extends Entity implements Collectable, Weapon {
    private static final String TYPE = "sword";
    public static final float DAMAGE = 2;
    public static final int HIT_MULTIPLIER = 1;
    
    private int durability;

    /**
     * Constructor for a sword
     * @param position
     */
    public Sword(Position position) {
        super(TYPE, position);
        this.durability = 10;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return collect();         
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean collect() {
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability <= 0) {
            InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
        }
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = super.toJsonObject();
        obj.addProperty("durability", durability);
        return obj;
    }
    /**
     * @param durability the durability to set
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }

    @Override
    public float getDamage() {
        return DAMAGE;
    }

    @Override
    public int getHitMultiplier() {
        return HIT_MULTIPLIER;
    }
}
