package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.floor_switch.FloorSwitch;
import dungeonmania.util.Direction;

public class FloorSwitchTest {

    @Test
    public void testUntriggeredSwitch() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("floorSwitchEmptyTest", "Standard");
        
        List<Entity> entities = Dungeon.getInstance().getEntities();
        FloorSwitch f = null;
        for (Entity e : entities) {
            if (e instanceof FloorSwitch) {
                f = (FloorSwitch) e;
            }
        }
        assertFalse(f.isTriggered());

        controller.tick(null, Direction.LEFT);
        assertFalse(f.isTriggered());
    }

    @Test
    public void testSwitchTrigger() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("floorSwitchTriggerTest", "Standard");
        
        List<Entity> entities = Dungeon.getInstance().getEntities();
        FloorSwitch f = null;
        for (Entity e : entities) {
            if (e instanceof FloorSwitch) {
                f = (FloorSwitch) e;
            }
        }
        assertFalse(f.isTriggered());
        assertTrue(f != null);

        controller.tick(null, Direction.LEFT);
        assertTrue(f.isTriggered());
        
        controller.tick(null, Direction.LEFT);
        assertFalse(f.isTriggered());
    }
}
