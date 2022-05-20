package dungeonmania.goals;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.floor_switch.FloorSwitch;
import dungeonmania.entities.util.EntityUtils;

public class BouldersGoal implements Goal {

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkGoal() {
        
        Dungeon dungeon = Dungeon.getInstance();

        // checks if all floor switches in dungeon are activated
        boolean all_boulders_triggered = EntityUtils.getEntitiesToTypeList(dungeon.getEntities(), FloorSwitch.class)
            .stream()
            .allMatch(s -> s.isTriggered());

        long num_boulders_not_triggered = EntityUtils.getEntitiesToTypeList(dungeon.getEntities(), FloorSwitch.class)
            .stream()
            .filter(s -> !s.isTriggered())
            .count();
        
        if (all_boulders_triggered == false) {
            
            return String.format(":boulders (%d)", num_boulders_not_triggered);
        }
        
        return "";
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("goal", "boulders");
        return obj;
    }
}
