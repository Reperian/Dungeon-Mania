package dungeonmania;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;


public class ArmourTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
	public void oneArmourGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("oneArmourGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "armour").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("armour")).count()); 
    }

    @Test
	public void mulitpleArmoursGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("mulitpleArmoursGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "armour").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("armour")).count());

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);
        
        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("armour")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("armour")).count());
    }

    @Test
    public void armourDurabilityTest() {
        DungeonManiaController controller = new DungeonManiaController();
        
        DungeonResponse response = controller.newGame("armourDurabilityTest", "Standard");

        assertEquals(1, getEntityResponses(response, "armour").size());

        response = controller.tick(null, Direction.UP);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("armour")).count());

        List<EntityResponse> list = getEntityResponses(response, "spider");
        assertTrue(list.isEmpty() == false);

        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);
        list = getEntityResponses(response, "spider");

        assertTrue(list.isEmpty() == true);

        assertEquals(Dungeon.getInstance().getPlayer().getHealth(), 19);
    }
}
