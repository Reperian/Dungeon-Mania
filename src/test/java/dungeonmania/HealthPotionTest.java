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

public class HealthPotionTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
	public void oneHealthPotionGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();
        
        DungeonResponse response = controller.newGame("oneHealthPotionGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "health_potion").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());
    }

    @Test
	public void multipleHealthPotionsGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("multipleHealthPotionsGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "health_potion").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());
    }

    @Test
    public void useHealthPotionTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("useHealthPotionTest", "Standard");

        assertEquals(2, getEntityResponses(response, "health_potion").size());

        Player player = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Player.class).get(0);
        List<String> health = getEntityResponses(response, "health_potion").stream().map(e -> e.getId()).collect(Collectors.toList());
        player.setHealth(5);

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());

        response = controller.tick(health.get(0), Direction.NONE);

        assertEquals(15, player.getHealth());

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());

        response = controller.tick(health.get(1), Direction.NONE);

        assertEquals(20, player.getHealth());

        assertEquals(0, response.getInventory().stream().filter(e -> e.getType().equals("health_potion")).count());
    }
}
