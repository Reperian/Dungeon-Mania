package dungeonmania;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class TheOneRingTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
	public void oneTheOneRingGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        
        DungeonResponse response = controller.newGame("oneTheOneRingGetCollectedTest", "Standard");
        
        assertEquals(1, getEntityResponses(response, "the_one_ring").size());
        
        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);
        
        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());
    }

    @Test
	public void mulitpleTheOneRingsGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("mulitpleTheOneRingsGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "the_one_ring").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());      
        
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());
    }

    @Test
	public void useTheOneRingTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("useTheOneRingTest", "Standard");
        Player player = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Player.class).get(0);
        
        assertEquals(1, getEntityResponses(response, "the_one_ring").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());
        player.setHealth(0);
        
        assertEquals(20, player.getHealth());
        response = controller.tick(null, Direction.NONE);
        
        assertEquals(0, response.getInventory().stream().filter(e -> e.getType().equals("the_one_ring")).count());
    }
}

