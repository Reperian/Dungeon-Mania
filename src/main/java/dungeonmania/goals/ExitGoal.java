package dungeonmania.goals;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.exit.Exit;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;

public class ExitGoal implements Goal {

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkGoal() {
        
        Dungeon dungeon = Dungeon.getInstance();
        Player player = dungeon.getPlayer();

        // checks if exit has been reached by player
        boolean reached_exit = EntityUtils.getEntitiesOfTypeList(dungeon.getEntities(), Exit.class)
            .stream()
            .anyMatch(e -> e.getPosition().equals(player.getPosition()));
        
        // if the player has not reached the exit return exit goal is incomplete
        if (reached_exit == false) { 

            return ":exit";
            
        }
        
        return "";
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("goal", "exit");
        return obj;
    }
}
