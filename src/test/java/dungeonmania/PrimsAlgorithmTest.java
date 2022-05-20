package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.exit.Exit;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wall.Wall;
import dungeonmania.movement.algorithms.Dijkstra;
import dungeonmania.util.Position;

public class PrimsAlgorithmTest {
    @Test
    public void checkExitPathExists(){
        DungeonManiaController controller = new DungeonManiaController();
        controller.generateDungeon(1, 1, 48, 48, "Standard");
        Dijkstra dijkstra = new Dijkstra(EntityUtils.getEntitiesAtPosition(new Position(48, 48)).get(0));
        assertNotEquals(null, dijkstra.getNextPosition(new Position(1,1)));
    }
    @Test
    public void checkAllPathsConnected(){
        DungeonManiaController controller = new DungeonManiaController();
        controller.generateDungeon(1, 1, 48, 48, "Standard");
        Dijkstra dijkstra = new Dijkstra(EntityUtils.getEntitiesAtPosition(new Position(48, 48)).get(0));
        assertNotEquals(null, dijkstra.getNextPosition(new Position(1,1)));
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                List<Entity> entitiesAtPosition = EntityUtils.getEntitiesAtPosition(new Position(x, y));
                if (EntityUtils.getEntitiesOfTypeList(entitiesAtPosition, Wall.class).size() > 0 ||
                    EntityUtils.getEntitiesOfTypeList(entitiesAtPosition, Exit.class).size() > 0) {
                    continue;
                }
                assertNotEquals(null, dijkstra.getPreviousMap().get(new Position(x, y)));
            }
        }
    }
}
