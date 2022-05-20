package dungeonmania.entities.the_one_ring;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TheOneRing extends Entity implements Collectable {
    private static final String TYPE = "the_one_ring";

    /**
     * Constructor for The One Ring
     * @param position
     */
    public TheOneRing(Position position) {
        super(TYPE, position);
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
    public boolean collect() {
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * When the player's health is less than or equal to zero, set full health back
     */
    public void rarePower() {
        // Get the player's curent health
        int health = Dungeon.getInstance().getPlayer().getHealth();
        // If the player's current health less than or equal to zero, add FULL HEALTH to the player's health
        if (health <= 0) {
            Dungeon.getInstance().getPlayer().setHealth(Integer.MAX_VALUE);
        }
        // Remove the used One Ring from the player's inventory
        InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getInventory(), getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
