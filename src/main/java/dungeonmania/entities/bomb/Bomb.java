package dungeonmania.entities.bomb;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Usable;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bomb extends Entity implements Collectable, Usable {
    private static final String TYPE = "bomb";

    /**
     * Constructor for the bomb
     * @param position
     */
    public Bomb(Position position) {
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
    public void use() {
        Player player = Dungeon.getInstance().getPlayer();
        Position pos = player.getPosition();
        // places a bomb in the players current position
        Dungeon.getInstance().getEntities().add(new PlacedBomb(new Position(pos.getX(), pos.getY(), 1)));
        InventoryUtils.removeItemIdFromInventory(player.getInventory(), getId());
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
