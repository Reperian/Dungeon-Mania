package dungeonmania.dungeon.util;

import java.util.List;

import com.google.gson.JsonArray;

import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;

public class InventorySaver {
    /**
     * Converts the inventory to a jsonObject and returns JsonArray
     * @param inventory
     * @return JsonArray
     */
    public static JsonArray inventoryToJsonArray(List<Collectable> inventory) {
        JsonArray jsonArr = new JsonArray();
        inventory.stream().forEach(e -> jsonArr.add(((Entity)e).toJsonObject()));
        return jsonArr;
    }
}
