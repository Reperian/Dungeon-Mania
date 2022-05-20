package dungeonmania.entities.behaviours;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface Moveable {
    /**
     * Moves the entity to a position
     * @param translateBy
     */
    Direction move(Position translateBy);
    
}
