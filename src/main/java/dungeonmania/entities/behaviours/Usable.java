package dungeonmania.entities.behaviours;

import dungeonmania.exceptions.InvalidActionException;

public interface Usable {

    /**
     * Apply the item that can be used from inventory
     */
    public void use() throws IllegalArgumentException, InvalidActionException;
    
}
