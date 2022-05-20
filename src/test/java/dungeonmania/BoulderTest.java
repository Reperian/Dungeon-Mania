package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BoulderTest {
    
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    private static List<EntityResponse> getEntityResponsesAtPosition(DungeonResponse dungeonResponse, String type, Position position) {
        return dungeonResponse.getEntities().stream().filter(e->((e.getType().equals(type))&&(e.getPosition().equals(position)))).collect(Collectors.toList());
    }

    @Test
    public void moveBoulder() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("floorSwitchTriggerTest", "Standard");
        
        List<EntityResponse> boulders = getEntityResponses(info, "boulder");

        assertEquals(new Position(3,2), boulders.get(0).getPosition());

        info = controller.tick(null, Direction.LEFT);
        boulders = getEntityResponses(info, "boulder");
        assertEquals(new Position(2,2), boulders.get(0).getPosition());
    }

    @Test
    public void moveTwoBoulders() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("boulders", "Standard");

        EntityResponse player = getEntityResponses(info, "player").get(0);

        List<EntityResponse> topBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,3));

        List<EntityResponse> bottomBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,4));

        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.RIGHT); // get player in a position to 'move' 2 boulders at once
        player = getEntityResponses(info, "player").get(0);
        topBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,3));
        bottomBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,4));
        assertEquals(new Position(4,2), player.getPosition());
        assertTrue(topBoulder.size() == 1);
        assertTrue(bottomBoulder.size() == 1);

        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        topBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,3));
        bottomBoulder = getEntityResponsesAtPosition(info, "boulder", new Position(4,4));
        assertEquals(new Position(4,2), player.getPosition());
        assertTrue(topBoulder.size() == 1);
        assertTrue(bottomBoulder.size() == 1);
    }

    @Test
    public void moveBoulderIntoOtherEntitiesGroup1() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("boulderTestStaticEntities1", "Standard");

        EntityResponse boulder = getEntityResponses(info, "boulder").get(0);

        for (int i = 0; i < 4; i++) {
            assertEquals(new Position(i, 2), boulder.getPosition());
        
            info = controller.tick(null, Direction.UP);
            boulder = getEntityResponses(info, "boulder").get(0);
            assertEquals(new Position(i, 2), boulder.getPosition());

            controller.tick(null, Direction.LEFT);
            controller.tick(null, Direction.UP);
            controller.tick(null, Direction.RIGHT);
            controller.tick(null, Direction.DOWN);
            info = controller.tick(null, Direction.RIGHT);
            boulder = getEntityResponses(info, "boulder").get(0);
        }
    }

    @Test
    public void moveBoulderIntoOtherEntitiesGroup2() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("boulderTestStaticEntities2", "Standard");

        EntityResponse boulder = getEntityResponses(info, "boulder").get(0);

        assertEquals(new Position(0, 2), boulder.getPosition());
        
        info = controller.tick(null, Direction.UP);
        boulder = getEntityResponses(info, "boulder").get(0);
        assertEquals(new Position(0, 2), boulder.getPosition());

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);
        boulder = getEntityResponses(info, "boulder").get(0);
        assertEquals(new Position(1, 2), boulder.getPosition());
        
        info = controller.tick(null, Direction.UP);
        boulder = getEntityResponses(info, "boulder").get(0);
        assertEquals(new Position(1, 1), boulder.getPosition());

        info = controller.tick(null, Direction.UP);
        boulder = getEntityResponses(info, "boulder").get(0);
        assertEquals(new Position(1, 0), boulder.getPosition());
    }
}
