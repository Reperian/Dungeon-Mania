package dungeonmania.entities.health_potion;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Usable;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HealthPotion extends Entity implements Collectable, Usable {
    private static final String TYPE = "health_potion";
    private static int RECOVERY = 10;
    
    /**
     * Constructor for the health potion
     * @param position
     */
    public HealthPotion(Position position) {
        super(TYPE, position);
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
    public void use() {
        // Get the player's current health
        int health = Dungeon.getInstance().getPlayer().getHealth();
        // Add the RECOVERY to the player's current health
        Dungeon.getInstance().getPlayer().setHealth(health + RECOVERY);
        // Remove the used health potion from the player's inventory
        InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
