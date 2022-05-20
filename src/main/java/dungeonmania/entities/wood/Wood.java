package dungeonmania.entities.wood;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Wood extends Entity implements Collectable {
    private static final String TYPE = "wood";
    
    /**
     * Constructor for the wood
     * @param position
     */
    public Wood(Position position) {
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
}
