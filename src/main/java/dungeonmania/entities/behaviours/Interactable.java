package dungeonmania.entities.behaviours;

import dungeonmania.exceptions.InvalidActionException;

public interface Interactable {
    /**
     * Invokes an interaction with this object
     * @return
     */
    public boolean interact() throws IllegalArgumentException, InvalidActionException;
}
