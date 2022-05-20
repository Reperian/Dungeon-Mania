package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import utils.TestUtil;

public class MercenaryInteractionTest {
    @Test
    public void testMercenaryBribeWithCoin(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("mercenaryBribe", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
        EntityResponse mercenaryResp = TestUtil.getEntityResponses(resp, "mercenary").get(0);
        assertEquals(0, resp.getInventory().size()); 
        resp = controller.tick(null, Direction.RIGHT);
        assertEquals(1, resp.getInventory().size()); 
        resp = controller.tick(null, Direction.RIGHT);
        resp = assertDoesNotThrow(() -> (controller.interact(mercenaryResp.getId())));
        assertEquals(0, resp.getInventory().size());
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary").size());
    }
    @Test
    public void testMercenaryBribeWithoutCoin(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("mercenaryBribe", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
        
        EntityResponse mercenaryResp = TestUtil.getEntityResponses(resp, "mercenary").get(0);
        assertEquals(0, resp.getInventory().size()); 
        resp = controller.tick(null, Direction.LEFT);
        resp = controller.tick(null, Direction.NONE);
        resp = controller.tick(null, Direction.NONE);
        resp = controller.tick(null, Direction.NONE);
        resp = controller.tick(null, Direction.NONE);
        assertThrows(InvalidActionException.class, () -> controller.interact(mercenaryResp.getId()));
        resp = controller.tick(null, Direction.LEFT);
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
    }
    @Test
    public void testMercenaryBribeOutOfRange(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse resp = controller.newGame("mercenaryBribe", "Standard");
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
        EntityResponse mercenaryResp = TestUtil.getEntityResponses(resp, "mercenary").get(0);
        assertEquals(0, resp.getInventory().size());
        assertThrows(InvalidActionException.class, () -> controller.interact(mercenaryResp.getId()));
        resp = controller.tick(null, Direction.LEFT);
        assertEquals(1, TestUtil.getEntityResponses(resp, "mercenary").size());
        assertEquals(0, TestUtil.getEntityResponses(resp, "mercenary_friendly").size());
    }
}
