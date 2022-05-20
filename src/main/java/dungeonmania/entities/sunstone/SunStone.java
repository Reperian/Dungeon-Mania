package dungeonmania.entities.sunstone;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Usable;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunStone extends Entity implements Collectable, Usable {
    private static final String TYPE = "sun_stone";

    /**
     * Constructor for the sun stone
     * @param position
     */
    public SunStone(Position position) {
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
    public boolean collect() {
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
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
    public void init() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void use() throws IllegalArgumentException, InvalidActionException {
        Treasure treasure = new Treasure(null);
        Dungeon.getInstance().getPlayer().addItemToInventory(treasure);
    }
    
}
