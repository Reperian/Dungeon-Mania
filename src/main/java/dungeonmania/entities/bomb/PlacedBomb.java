package dungeonmania.entities.bomb;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.floor_switch.FloorSwitch;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PlacedBomb extends Entity {
    private static final String TYPE = "bomb_placed";
    /**
     * Constructor for the placed bomb
     * @param position
     */
    public PlacedBomb(Position position) {
        super(TYPE, position);
    }

    /**
     * Causes the bomb to kill/remove every entity except the player in the explosion radius
     */
    private void explode() {
        // For each adjacent position to the bomb
        List<Position> adjacent = getPosition().getAdjacentPositions();
        adjacent.add(this.getPosition());
        for (Position pos : adjacent) {
            // Remove each entity thats not the player
            for (Entity entity : EntityUtils.getEntitiesAtPosition(pos)) {
                if (entity instanceof Player) {
                    continue;
                }
                Dungeon.getInstance().getEntities().remove(entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        // check for adjacent placed pressure plates that are activated
        List<Position> adjacent = getPosition().getAdjacentFour();
        for (Position pos : adjacent) {
            for (Entity entity : EntityUtils.getEntitiesAtPosition(pos)) {
                if (entity instanceof FloorSwitch && ((FloorSwitch)entity).isTriggered()) {
                    explode();
                    break;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
