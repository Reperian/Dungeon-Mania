package dungeonmania.entities.wall;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Wall extends Entity {
    private static final String TYPE = "wall";
    
    /**
     * Constructor for a wall
     * @param position
     */
    public Wall(Position position) {
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
        return false;         
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
