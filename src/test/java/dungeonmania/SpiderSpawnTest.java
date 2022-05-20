package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class SpiderSpawnTest {
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    @Test
    public void testSpiderNormalSpawn() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("spiderSpawnTest", "Standard");
        for (int i = 0; i < 19; i++) {
            resp = controller.tick(null, Direction.NONE);
        }
        assertEquals(0, getEntityResponses(resp, "spider").size());
        resp = controller.tick(null, Direction.NONE);
        assertEquals(1, getEntityResponses(resp, "spider").size());
    }
    
    @Test
    public void testSpiderMaxLimit() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("spiderSpawnTest", "Standard");
        for (int num = 0; num < 4; num++) {
            for (int i = 0; i < 19; i++) {
                controller.tick(null, Direction.NONE);
            }
            resp = controller.tick(null, Direction.NONE);
            assertEquals(num + 1, getEntityResponses(resp, "spider").size());
        }
        for (int i = 0; i < 40; i++) {
            resp = controller.tick(null, Direction.NONE);
        }
        assertEquals(4, getEntityResponses(resp, "spider").size());
    }


    @Test
    public void testSpiderNoRoom() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("spiderNoSpawningRoom", "Standard");
        for (int num = 0; num < 4; num++) {
            for (int i = 0; i < 20; i++) {
                resp = controller.tick(null, Direction.NONE);
            }
        }
        assertEquals(0, getEntityResponses(resp, "spider").size());
    }
    @Test
    public void testSpiderSpawnAfterVacantPosition() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("spiderNoSpawningRoom", "Standard", 5);
        for (int num = 0; num < 4; num++) {
            for (int i = 0; i < 20; i++) {
                resp = controller.tick(null, Direction.NONE);
            }
        }
        assertEquals(0, getEntityResponses(resp, "spider").size());
        resp = controller.tick(null, Direction.LEFT);
        for (int num = 0; num < 4; num++) {
            for (int i = 0; i < 20; i++) {
                resp = controller.tick(null, Direction.NONE);
            }
        }
        assertEquals(1, getEntityResponses(resp, "spider").size());
    }
}
