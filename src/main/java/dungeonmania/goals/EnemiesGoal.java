package dungeonmania.goals;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.zombie_toast_spawner.ZombieToastSpawner;

public class EnemiesGoal implements Goal {

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkGoal() {
        
        Dungeon dungeon = Dungeon.getInstance();
        List<Entity> entities = dungeon.getEntities();

        // check the number of battleables and spawners in the dungeon
        int num_enemies = EntityUtils.getEntitiesOfTypeList(entities, Battleable.class).size() + EntityUtils.getEntitiesOfTypeList(entities, ZombieToastSpawner.class).size();
  
        if (num_enemies > 0) { 

            return String.format(":enemies (%d)", num_enemies);
        }
        
        return "";
    } 
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("goal", "enemies");
        return obj;
    }
}
