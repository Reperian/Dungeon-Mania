package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import utils.TestUtil;

public class BombTest {
    @Test
    public void testBombNormal() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("bombTest", "Standard");
        String bombID = TestUtil.getEntityResponses(response, "bomb").get(0).getId();
        int initialEntitiesCount = response.getEntities().size();

        controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(response.getInventory().get(0).getType().equals("bomb"));
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
       
        response = controller.tick(bombID, Direction.NONE);
        assertEquals(0, response.getInventory().size()); 
        assertEquals(initialEntitiesCount, response.getEntities().size());
     
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        
        assertEquals(initialEntitiesCount - 8, response.getEntities().size());
    }

    @Test
    public void testBombNoAdjacentSwitch() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("bombTest", "Standard");
        String bombID = TestUtil.getEntityResponses(response, "bomb").get(0).getId();
        int initialEntitiesCount = response.getEntities().size();
      
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
       
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
    
        controller.tick(bombID, Direction.NONE);
     
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
     
        assertEquals(initialEntitiesCount, response.getEntities().size());
    }

    @Test
    public void testBombPlaceNearActiveSwitch() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("bombTest", "Standard");
        String bombID = TestUtil.getEntityResponses(response, "bomb").get(0).getId();
        int initialEntitiesCount = response.getEntities().size();
     
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
  
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
     
        response = controller.tick(bombID, Direction.NONE);
        assertEquals(initialEntitiesCount - 6, response.getEntities().size());
    }
    
    @Test
    public void testBombCannotPickupOncePlaced() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("bombTest", "Standard");
        String bombID = TestUtil.getEntityResponses(response, "bomb").get(0).getId();
        int initialEntitiesCount = response.getEntities().size();
        controller.tick(null, Direction.LEFT);
        controller.tick(bombID, Direction.NONE);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.LEFT);

        assertEquals(initialEntitiesCount, response.getEntities().size());
    }
}
