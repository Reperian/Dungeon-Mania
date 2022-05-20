package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.player.Player;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SunStoneTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
    public void testOneSunStoneGetCollected() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("oneSunStoneGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "sun_stone").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());
    }

    @Test
	public void testMultipleSunStoneGetCollected() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("mulitpleSunStonesGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "sun_stone").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);
        
        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());
    }

    @Test
    public void testUseSunStoneOpenDoors() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("useSunStoneOpenDoorsTest", "Standard");
        Player player = Dungeon.getInstance().getPlayer();

        assertEquals(new Position(0,1), player.getPosition());

        assertEquals(1, getEntityResponses(response, "sun_stone").size());

        assertEquals(1, getEntityResponses(response, "door_locked_1").size());
        assertEquals(1, getEntityResponses(response, "door_locked_2").size());       

        response = controller.tick(null, Direction.UP); 
        assertEquals(new Position(0,0), player.getPosition());
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1,0), player.getPosition());
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2,0), player.getPosition());
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2,1), player.getPosition());
        response = controller.tick(null, Direction.DOWN);
        assertEquals(new Position(2,2), player.getPosition());
        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());
    }

    @Test
    public void testUseSunStoneInterChangeTreasure() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("useSunStoneGetTreasureTest", "Standard");
        List<String> sunStones = getEntityResponses(response, "sun_stone").stream().map(e -> e.getId()).collect(Collectors.toList());

        assertEquals(1, getEntityResponses(response, "sun_stone").size());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());
        assertEquals(0, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());

        response = controller.tick(sunStones.get(0), Direction.NONE);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());

        response = controller.tick(sunStones.get(0), Direction.NONE);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sun_stone")).count());

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("treasure")).count());
    }
}
