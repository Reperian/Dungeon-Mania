package dungeonmania.entities.armour;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Defence;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Armour extends Entity implements Collectable, Defence {
    private static final String TYPE = "armour";
    private static final float DAMAGE_REDUCTION = 0.5f;
    private static final int MAX_DURABILITY = 20; // change this

    private int durability = MAX_DURABILITY;

    /**
     * First constructor for the armour
     * @param position
     */
    public Armour(Position position) {
        this(MAX_DURABILITY, position);
    }

    /**
     * Second constructor for the armour
     * @param durability
     * @param position
     */
    public Armour(int durability, Position position) {
        super(TYPE, position);
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
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
    public boolean onOverlap(Entity entity, Direction dir) {
        return collect();   
    }
    
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
    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability <= 0) {
            InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
        }
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
    public JsonObject toJsonObject() {
        JsonObject obj = super.toJsonObject();
        obj.addProperty("durability", durability);
        return obj;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getFlatDefence() {
        return 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getPercentageDefence() {
        return DAMAGE_REDUCTION;
    }
}