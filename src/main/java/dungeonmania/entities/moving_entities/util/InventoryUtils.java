package dungeonmania.entities.moving_entities.util;

import java.util.List;

import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.Entity;

public class InventoryUtils {
    /**
     * removes item with id "id" from the inventory
     * @param inventory
     * @param id
     * @return
     */
    public static boolean removeItemIdFromInventory(List<Collectable> inventory, String id) {
        return inventory.removeIf(e -> ((Entity)e).getId().equals(id));                                      
    }
}
