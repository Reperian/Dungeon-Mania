package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.player.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import utils.TestUtil;

public class BossTest {
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
    public void testAssassinBribe() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("assassinTest", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "assassin").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "assassin_friendly").size());
        EntityResponse assassinResp = TestUtil.getEntityResponses(resp, "assassin").get(0);
        assertEquals(0, resp.getInventory().size());

        resp = controller.tick(null, Direction.RIGHT);
        assertEquals(1, resp.getInventory().size());

        resp = controller.tick(null, Direction.RIGHT);
        assertEquals(2, resp.getInventory().size()); 
        resp = assertDoesNotThrow(() -> (controller.interact(assassinResp.getId())));
        assertEquals(0, resp.getInventory().size());
        assertEquals(1, TestUtil.getEntityResponses(resp, "assassin_friendly").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "assassin").size());
    }

    @Test
    public void testAssassinBribeNoRing() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("assassinTest", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "assassin").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "assassin_friendly").size());
        EntityResponse assassinResp = TestUtil.getEntityResponses(resp, "assassin").get(0);
        assertEquals(0, resp.getInventory().size());

        resp = controller.tick(null, Direction.RIGHT);
        assertEquals(1, resp.getInventory().size());

        resp = controller.tick(null, Direction.NONE);
        assertEquals(1, resp.getInventory().size()); 
        assertThrows(InvalidActionException.class, () -> controller.interact(assassinResp.getId()));
    }

    @Test
    public void testAssassinBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("assassinTest", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "assassin").size());

        resp = controller.tick(null, Direction.RIGHT);
        resp = controller.tick(null, Direction.NONE);
        resp = controller.tick(null, Direction.NONE);
        resp = controller.tick(null, Direction.NONE);
        assertEquals(Dungeon.getInstance().getPlayer().getHealth(), 8);
    }

    @Test
    public void testAssassinSpawn() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("protected_player", "Standard", 5);
        assertEquals(0, getEntityResponses(resp, "assassin").size());
        resp = controller.tick(null, Direction.RIGHT);
        for (int i = 0; i < 149; i++) {
            resp = controller.tick(null, Direction.NONE);
        }
        assertEquals(1, getEntityResponses(resp, "assassin").size());      
    }

    @Test
    public void testHydraBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("hydraTest", "Standard", 0);
        assertEquals(1, TestUtil.getEntityResponses(resp, "hydra").size());
        List<dungeonmania.entities.Entity> list1 = Dungeon.getInstance().getEntities().stream().filter(e -> e.getType().equals("hydra")).collect(Collectors.toList());
        assertEquals(1, list1.size());
        
        resp = controller.tick(null, Direction.RIGHT);
        Player p1 = Dungeon.getInstance().getPlayer();

        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse resp2 = controller2.newGame("hydraTest", "Standard", 1);
        assertEquals(1, TestUtil.getEntityResponses(resp2, "hydra").size());
        List<dungeonmania.entities.Entity> list2 = Dungeon.getInstance().getEntities().stream().filter(e -> e.getType().equals("hydra")).collect(Collectors.toList());
        assertEquals(1, list2.size());
        
        resp2 = controller.tick(null, Direction.RIGHT);
        Player p2 = Dungeon.getInstance().getPlayer();
        assertNotEquals(p1.getHealth(), p2.getHealth()); 
    }

    @Test 
    public void testHydraSpawnOnStandard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("protected_player", "Standard", 0);
        assertEquals(0, getEntityResponses(resp, "hydra").size());
        resp = controller.tick(null, Direction.RIGHT);
        for (int i = 0; i < 49; i++) {
            resp = controller.tick(null, Direction.NONE);
        }
        assertEquals(0, getEntityResponses(resp, "hydra").size());      
    }

    @Test 
    public void testHydraSpawnOnHard() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("protected_player", "Hard", 0);
        assertEquals(0, getEntityResponses(resp, "hydra").size());
        resp = controller.tick(null, Direction.RIGHT);
        for (int i = 0; i < 49; i++) {
            resp = controller.tick(null, Direction.NONE);
        }
        assertEquals(1, getEntityResponses(resp, "hydra").size());      
    }
}
