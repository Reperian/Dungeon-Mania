package dungeonmania;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class KeyTest {
    
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
	public void oneKeyGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("oneKeyGetCollectedTest", "Standard");
        
        assertEquals(1, getEntityResponses(response, "key_1").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        
        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("key_1")).count());
    }

    @Test
	public void oneKeyOnlyAtOneTimeTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("oneKeyOnlyAtOneTimeTest", "Standard");
        
        assertEquals(1, getEntityResponses(response, "key_1").size());
        assertEquals(1, getEntityResponses(response, "key_2").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        
        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("key_1")).count());
        
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.LEFT);

        assertEquals(0, response.getInventory().stream().filter(e -> e.getType().equals("key_2")).count());
    }
}
