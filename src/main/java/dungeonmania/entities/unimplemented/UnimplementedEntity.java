package dungeonmania.entities.unimplemented;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class UnimplementedEntity extends Entity {
    private static final String TYPE = "unimplemented";
    public UnimplementedEntity(Position position) {
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
    public void init() {
    }
}
