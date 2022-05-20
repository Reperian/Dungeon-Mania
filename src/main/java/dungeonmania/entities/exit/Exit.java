package dungeonmania.entities.exit;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Exit extends Entity {
    private static final String TYPE = "exit";
    
    /** 
    * Constructor for an exit
    * @param position the dungeon position of the exit
    */
    public Exit(Position position) {
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
        return true;         
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void init() {}
}
