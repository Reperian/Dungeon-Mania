package dungeonmania;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class SwordTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType() == type)).collect(Collectors.toList());
    }
    
    /**
     * Test if one sword is collected or not
     */
    @Test
	public void oneSwordGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("oneSwordGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "sword").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count()); 
    }
    
    /**
     * Test if multiple swords are collected or not
     */
    @Test
	public void mulitpleSwordsGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("mulitpleSwordsGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "sword").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count());      

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.LEFT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count());
    }

}
