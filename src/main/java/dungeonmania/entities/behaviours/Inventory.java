package dungeonmania.entities.behaviours;

import java.util.List;

public interface Inventory {
    /**
     * returns the inventory of the entity
     * @return List<Collectable> 
     */
    public List<Collectable> getInventory();
}
