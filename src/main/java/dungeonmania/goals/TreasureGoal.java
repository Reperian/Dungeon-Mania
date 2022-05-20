package dungeonmania.goals;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.util.EntityUtils;

public class TreasureGoal implements Goal {

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkGoal() {

        Dungeon dungeon = Dungeon.getInstance();

        // checks if there is any treasure left in the dungeon

        int num_treasure = EntityUtils.getEntitiesOfTypeList(dungeon.getEntities(), Treasure.class).size();

        if (num_treasure > 0) {

            return String.format(":treasure (%d)", num_treasure);
        }
        
        return "";
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("goal", "treasure");
        return obj;
    }
}
