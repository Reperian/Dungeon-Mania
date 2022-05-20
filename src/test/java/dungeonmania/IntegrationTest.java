package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class IntegrationTest {
    
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    private static List<ItemResponse> getItemResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getInventory().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
    public void playBoulders(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("boulders", "Standard");
        assertTrue(info.getGoals().contains("boulders"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);

        if (getEntityResponses(info, "spider").size() == 0) {
            assertEquals("", info.getGoals());
        } 
    }

    @Test
    public void playMaze(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("maze", "Standard");
        assertTrue(info.getGoals().contains("exit"));
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);
        assertEquals("", info.getGoals());
    }

    @Test
    public void playAdvanced(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("advanced", "Standard", 1);
        assertTrue(info.getGoals().contains("treasure"));
        assertTrue(info.getGoals().contains("enemies"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> sword = getEntityResponses(info, "sword");
        assertEquals(1, sword.size());
        info = controller.tick(null, Direction.RIGHT);
        List<ItemResponse> sword_items = getItemResponses(info, "sword");
        assertEquals(1, sword_items.size());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> bomb = getEntityResponses(info, "bomb");
        assertEquals(1, bomb.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> bomb_items = getItemResponses(info, "bomb");
        assertEquals(1, bomb_items.size());

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);

        List<EntityResponse> invincibility_potion = getEntityResponses(info, "invincibility_potion");
        assertEquals(1, invincibility_potion.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> invincibility_potion_items = getItemResponses(info, "invincibility_potion");
        assertEquals(1, invincibility_potion_items.size());

        EntityResponse player = getEntityResponses(info, "player").get(0);  
        assertEquals(new Position(11, 10), player.getPosition());

        controller.tick(null, Direction.DOWN);

        controller.saveGame("PlayingAdvancedTest");
        info = controller.loadGame("PlayingAdvancedTest");

        player = getEntityResponses(info, "player").get(0);  
        assertEquals(new Position(11, 11), player.getPosition());

        assertEquals(3, info.getInventory().size());
        assertEquals(0, getEntityResponses(info, "sword").size());
        assertEquals(0, getEntityResponses(info, "bomb").size());
        assertEquals(0, getEntityResponses(info, "incinvibility_potion").size());
        assertEquals(1, getEntityResponses(info, "mercenary").size());
        assertEquals(1, getEntityResponses(info, "treasure").size());
        assertEquals(1, getEntityResponses(info, "spider").size());

        bomb_items = getItemResponses(info, "bomb");
        assertEquals(1, bomb_items.size());
        info = controller.tick(bomb_items.get(0).getId(), Direction.DOWN);
        bomb_items = getItemResponses(info, "bomb");
        assertEquals(0, bomb_items.size());
        List<EntityResponse> placed_bomb = getEntityResponses(info, "bomb_placed"); 
        assertEquals(1, placed_bomb.size());

        info = controller.tick(null, Direction.UP);
        player = getEntityResponses(info, "player").get(0);   
        assertNotEquals(placed_bomb.get(0).getPosition(),player.getPosition()); 
        EntityResponse mercenary = getEntityResponses(info, "mercenary").get(0); 
        assertNotEquals(placed_bomb.get(0).getPosition(),mercenary.getPosition()); 

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.LEFT);
        
        mercenary = getEntityResponses(info, "mercenary").get(0); 
        player = getEntityResponses(info, "player").get(0);
        assertEquals(player.getPosition().translateBy(Direction.UP), mercenary.getPosition());
        info = controller.tick(null, Direction.UP);
        List<EntityResponse> mercenaries = getEntityResponses(info, "mercenary");
        assertEquals(0, mercenaries.size());
        List<EntityResponse> player_list = getEntityResponses(info, "player");
        assertEquals(1, player_list.size());

        info = controller.tick(null, Direction.UP);

        List<EntityResponse> treasure = getEntityResponses(info, "treasure");
        assertEquals(1, treasure.size());
        info = controller.tick(null, Direction.UP);
        List<ItemResponse> treasure_items = getItemResponses(info, "treasure");
        assertEquals(1, treasure_items.size());

        assertFalse(info.getGoals().contains("treasure"));

        if (getEntityResponses(info, "spider").size() == 0) {
            assertEquals("", info.getGoals());
        } 
    }

    @Test
    public void playAdvanced2(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("advanced-2", "Standard");
        assertTrue(info.getGoals().contains("treasure"));
        assertTrue(info.getGoals().contains("enemies"));
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> sword = getEntityResponses(info, "sword");
        assertEquals(1, sword.size());
        info = controller.tick(null, Direction.RIGHT);
        List<ItemResponse> sword_items = getItemResponses(info, "sword");
        assertEquals(1, sword_items.size());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> bomb = getEntityResponses(info, "bomb");
        assertEquals(1, bomb.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> bomb_items = getItemResponses(info, "bomb");
        assertEquals(1, bomb_items.size());

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);

        List<EntityResponse> key_silver = getEntityResponses(info, "key_1");
        assertEquals(1, key_silver.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> key_silver_items = getItemResponses(info, "key_1");
        assertEquals(1, key_silver_items.size());

        List<EntityResponse> key_gold = getEntityResponses(info, "key_2");
        assertEquals(1, key_gold.size());
        info = controller.tick(null, Direction.RIGHT);
        key_gold = getEntityResponses(info, "key_2");
        assertEquals(1, key_gold.size());
        List<ItemResponse> key_gold_items = getItemResponses(info, "key_2");
        assertEquals(0, key_gold_items.size());

        info = controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT);

        List<EntityResponse> door_locked_silver = getEntityResponses(info, "door_locked_1");
        assertEquals(1, door_locked_silver.size());
        info = controller.tick(null, Direction.UP);
        door_locked_silver = getEntityResponses(info, "door_locked_1");
        assertEquals(0, door_locked_silver.size());
        List<EntityResponse> door_unlocked_silver = getEntityResponses(info, "door_unlocked_1");
        assertEquals(1, door_unlocked_silver.size());
        key_silver_items = getItemResponses(info, "key_1");
        assertEquals(0, key_silver_items.size());

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);

        List<EntityResponse> wood = getEntityResponses(info, "wood");
        assertEquals(3, wood.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> wood_items = getItemResponses(info, "wood");
        assertEquals(1, wood_items.size());
        wood = getEntityResponses(info, "wood");
        assertEquals(2, wood.size());
        info = controller.tick(null, Direction.DOWN);
        wood_items = getItemResponses(info, "wood");
        assertEquals(2, wood_items.size());
        wood = getEntityResponses(info, "wood");
        assertEquals(1, wood.size());

        List<EntityResponse> arrows = getEntityResponses(info, "arrow");
        assertEquals(3, arrows.size());
        info = controller.tick(null, Direction.DOWN);
        List<ItemResponse> arrow_items = getItemResponses(info, "arrow");
        assertEquals(1, arrow_items.size());
        arrows = getEntityResponses(info, "arrow");
        assertEquals(2, arrows.size());
        info = controller.tick(null, Direction.DOWN);
        arrow_items = getItemResponses(info, "arrow");
        assertEquals(2, arrow_items.size());
        arrows = getEntityResponses(info, "arrow");
        assertEquals(1, arrows.size());
        info = controller.tick(null, Direction.RIGHT);
        arrow_items = getItemResponses(info, "arrow");
        assertEquals(3, arrow_items.size());
        arrows = getEntityResponses(info, "arrow");
        assertEquals(0, arrows.size());

        info = controller.tick(null, Direction.RIGHT);
        wood_items = getItemResponses(info, "wood");
        assertEquals(3, wood_items.size());
        wood = getEntityResponses(info, "wood");
        assertEquals(0, wood.size());

        List<ItemResponse> bow_items = getItemResponses(info, "bow");
        assertEquals(0, bow_items.size());
        info = controller.build("bow");
        bow_items = getItemResponses(info, "bow");
        assertEquals(1, bow_items.size());
        wood_items = getItemResponses(info, "wood");
        assertEquals(2, wood_items.size());
        arrow_items = getItemResponses(info, "arrow");
        assertEquals(0, arrow_items.size());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.LEFT);

        key_gold = getEntityResponses(info, "key_2");
        assertEquals(1, key_gold.size());
        info = controller.tick(null, Direction.LEFT);
        key_gold = getEntityResponses(info, "key_2");
        assertEquals(0, key_gold.size());
        key_gold_items = getItemResponses(info, "key_2");
        assertEquals(1, key_gold_items.size());

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.LEFT);

        List<EntityResponse> mercenaries = getEntityResponses(info, "mercenary");
        assertTrue(mercenaries.size() >= 1);
        int old_merc_size = mercenaries.size();
        info = controller.interact(mercenaries.get(0).getId());
        mercenaries = getEntityResponses(info, "mercenary");
        assertEquals(old_merc_size - 1, mercenaries.size());
        List<EntityResponse> ally_mercenaries = getEntityResponses(info, "mercenary_friendly");
        assertEquals(1, ally_mercenaries.size());

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.UP);

        List<EntityResponse> door_locked_gold = getEntityResponses(info, "door_locked_2");
        assertEquals(1, door_locked_gold.size());
        info = controller.tick(null, Direction.UP);
        door_locked_gold = getEntityResponses(info, "door_locked_2");
        assertEquals(0, door_locked_gold.size());
        List<EntityResponse> door_unlocked_gold = getEntityResponses(info, "door_unlocked_2");
        assertEquals(1, door_unlocked_gold.size());
        key_gold_items = getItemResponses(info, "key_2");
        assertEquals(0, key_gold_items.size());
    }
}
