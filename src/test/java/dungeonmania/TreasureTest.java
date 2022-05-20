package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class TreasureTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
	public void oneTreasureGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        
        DungeonResponse response = controller.newGame("oneTreasureGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "treasure").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());                           
    }

    @Test
	public void multipleTreasuresGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        
        DungeonResponse response = controller.newGame("multipleTreasuresGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "treasure").size());

        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);

        response = controller.tick(null, Direction.DOWN);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());

        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        
        response = controller.tick(null, Direction.UP);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());
        
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);

        response = controller.tick(null, Direction.DOWN);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());
    }
}
