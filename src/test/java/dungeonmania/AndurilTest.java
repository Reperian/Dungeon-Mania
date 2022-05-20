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

public class AndurilTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType() == type)).collect(Collectors.toList());
    }

    /**
     * Test if the Anduril is collected or not
     */
    @Test
    public void testOneAndurilGetCollected() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("oneAndurilGetCollectedTest", "Standard");

        assertEquals(1, getEntityResponses(response, "anduril").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.UP);
        response = controller.tick(null, Direction.UP);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());
    }

    /**
     * Test if multiple andurils are collected or not
     */
    @Test
    public void testMultipleAndurilsGetCollected() {
        
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("mulitpleAndurilsGetCollectedTest", "Standard");

        assertEquals(3, getEntityResponses(response, "anduril").size());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());       

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.LEFT);

        assertEquals(2, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(3, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());
    }

    @Test
    public void testUseAndurilOnlyKillHydra() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("killHydraByAndurilTest", "Standard", 1);
        Player player = Dungeon.getInstance().getPlayer();

        assertEquals(new Position(2, 5), player.getPosition());
        assertEquals(1, getEntityResponses(response, "hydra").size());
        assertEquals(1, getEntityResponses(response, "anduril").size());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());

        response = controller.tick(null, Direction.RIGHT); // Start battle
        
        assertEquals(0, getEntityResponses(response, "hydra").size());
        assertEquals(17, player.getHealth());
    }

    @Test
    public void testUseAndurilwithSwordKillHydra() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("killHydraByAndurilTest", "Standard",1);
        Player player = Dungeon.getInstance().getPlayer();

        assertEquals(new Position(2, 5), player.getPosition());
        assertEquals(1, getEntityResponses(response, "anduril").size());
        assertEquals(1, getEntityResponses(response, "sword").size());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.LEFT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count());

        response = controller.tick(null, Direction.RIGHT); 
        response = controller.tick(null, Direction.RIGHT); 
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        
        assertEquals(0, getEntityResponses(response, "hydra").size());
        assertEquals(17, player.getHealth());

    }

    @Test
    public void testUseAndurilOnlyKillAssassin() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("killAssassinByAndurilTest", "Standard", 1);
        Player player = Dungeon.getInstance().getPlayer();

        assertEquals(new Position(2, 5), player.getPosition());
        assertEquals(1, getEntityResponses(response, "assassin").size());
        assertEquals(1, getEntityResponses(response, "anduril").size());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());

        response = controller.tick(null, Direction.RIGHT); 

        assertEquals(0, getEntityResponses(response, "assassin").size());
        assertEquals(10, player.getHealth());
    }

    @Test
    public void testUseAndurilwithSwordKillAssassin() {
        DungeonManiaController controller = new DungeonManiaController();

        DungeonResponse response = controller.newGame("killAssassinByAndurilTest", "Standard", 1);
        Player player = Dungeon.getInstance().getPlayer();

        assertEquals(new Position(2, 5), player.getPosition());
        assertEquals(1, getEntityResponses(response, "assassin").size());
        assertEquals(1, getEntityResponses(response, "anduril").size());
        assertEquals(1, getEntityResponses(response, "sword").size());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("anduril")).count());

        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.LEFT);

        assertEquals(1, response.getInventory().stream().filter(e -> e.getType().equals("sword")).count());

        response = controller.tick(null, Direction.RIGHT);

        assertEquals(0, getEntityResponses(response, "assassin").size());
        assertEquals(10, player.getHealth());
    }
}
