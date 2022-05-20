package dungeonmania.entities.invinciblity_potion;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Usable;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.entities.player.status_states.InvincibleState;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Entity implements Collectable, Usable {
    private static final String TYPE = "invincibility_potion";

    /**
     * Constructor for the invincible potion
     * @param position
     */
    public InvincibilityPotion(Position position) {
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
    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
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
    public void init() {
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void use() {
        Dungeon.getInstance().getPlayer().setStatus(new InvincibleState());
        InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
    }
}
