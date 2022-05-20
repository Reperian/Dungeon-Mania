package dungeonmania.dungeon.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import dungeonmania.entities.behaviours.Collectable;

public class InventoryLoader {
    /**
     * Loads the inventory as a list of collectables
     * @param inventoryArr
     * @param gameMode
     * @return
     */
    public static List<Collectable> getInventory(JsonElement inventoryArr, String gameMode) {
        List<Collectable> inventory = new ArrayList<>();
        // handle entity does not have inventory case
        if (inventoryArr.getAsJsonObject().get("inventory") == null)
            return inventory;
        // Adds each entity as an collectable to the collectable list
        for (JsonElement e : inventoryArr.getAsJsonObject().get("inventory").getAsJsonArray()) {
            inventory.add((Collectable) EntityLoader.getEntity(e.getAsJsonObject(), gameMode));
        }
        return inventory;
    }
}
