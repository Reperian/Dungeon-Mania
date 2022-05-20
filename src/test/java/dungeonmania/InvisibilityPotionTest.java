package dungeonmania;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class InvisibilityPotionTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e -> (e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
    public void oneInvisiblePotionGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("oneInvisiblePotionGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "invisibility_potion").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());
    }

    @Test
    public void multipleInvisiblePotionsGetCollectedTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("multipleInvisiblePotionsGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "invisibility_potion").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());
    }

    @Test
    public void useInvinciblePotionTest() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("useInvisiblePotionTest", "Standard");

        List<String> invisiblePotion = getEntityResponses(response, "invisibility_potion").stream().map(e -> e.getId())
                .collect(Collectors.toList());

        assertEquals(1, getEntityResponses(response, "invisibility_potion").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());

        response = controller.tick(invisiblePotion.get(0), Direction.NONE);

        assertEquals(0, response.getInventory().stream().filter(e -> e.getType().equals("invisibility_potion")).count());
    }
}
