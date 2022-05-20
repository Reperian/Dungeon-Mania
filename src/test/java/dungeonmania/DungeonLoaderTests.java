package dungeonmania;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DungeonLoaderTests {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    private static String getFileName(String name, String id) {
        return name + "-" + id;
    }
    @Test
    public void testNewGameSimple() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");
        assertEquals(0, dungeonResponse.getInventory().size());
        assertEquals(120, dungeonResponse.getEntities().size());
    }

    @Test
    public void testNewGameCorrectEntityAmount() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("boulders", "Standard");
        assertEquals(50, dungeonResponse.getEntities().size());
        assertEquals(1, getEntityResponses(dungeonResponse, "player").size());
        assertEquals(7, getEntityResponses(dungeonResponse, "boulder").size());
        assertEquals(7, getEntityResponses(dungeonResponse, "switch").size());
        assertEquals(35, getEntityResponses(dungeonResponse, "wall").size());
    }

    @Test
    public void testNewGameCorrectEntityPosition() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("boulders", "Standard");
        assertEquals(new Position(2, 2), getEntityResponses(dungeonResponse, "player").get(0).getPosition());
    }

    @Test
    public void testNewGameInvalidGamemode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("boulders", "invalidmode"));
    }

    @Test
    public void testNewGameInvalidDungeonName() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> controller.newGame("invalidname", "Standard"));
    }
    
    @Test
    public void testSaveGameSimple() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");
        assertEquals(0, dungeonResponse.getInventory().size());
        assertEquals(120, dungeonResponse.getEntities().size());
        String fileName = getFileName(dungeonResponse.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(fileName);
        controller.allGames().contains(fileName);
    }
    @Test
    public void testLoadGameSimple() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");

        String save1 = getFileName(dungeonResponse.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(save1);
        controller.allGames().contains(save1);

        DungeonResponse loadedGame = controller.loadGame(save1);
        assertEquals(loadedGame.getDungeonName(), dungeonResponse.getDungeonName());
        assertEquals(loadedGame.getEntities().size(), dungeonResponse.getEntities().size());
        assertEquals(loadedGame.getInventory().size(), dungeonResponse.getInventory().size());
        assertEquals(loadedGame.getGoals(), dungeonResponse.getGoals());
    }
    @Test
    public void testLoadGameGoals() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");

        String save1 = getFileName(dungeonResponse.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(save1);
        DungeonResponse loadedGame = controller.loadGame(save1);
        assertEquals(loadedGame.getGoals(), dungeonResponse.getGoals());
    }
    @Test
    public void testSaveLoadPlayerMove() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");
        assertEquals(new Position(1, 1), getEntityResponses(dungeonResponse, "player").get(0).getPosition());
        String save1 = getFileName(dungeonResponse.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        
        controller.tick(null, Direction.RIGHT);
        controller.saveGame(save1);
        controller.allGames().contains(save1);

        DungeonResponse loadedGame = controller.loadGame(save1);
        assertEquals(new Position(2, 1), getEntityResponses(loadedGame, "player").get(0).getPosition());
    }
    @Test
    public void testSaveLoadPlayerInventory() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse dungeonResponse = controller.newGame("advanced", "Standard");
        assertEquals(new Position(1, 1), getEntityResponses(dungeonResponse, "player").get(0).getPosition());
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        dungeonResponse = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(6, 1), getEntityResponses(dungeonResponse, "player").get(0).getPosition());
        assertEquals("sword", dungeonResponse.getInventory().get(0).getType());
        
        String save1 = getFileName(dungeonResponse.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(save1);
        controller.allGames().contains(save1);

        DungeonResponse loadedGame = controller.loadGame(save1);
        assertEquals("sword", loadedGame.getInventory().get(0).getType());
    }

    @Test
    public void testSaveLoadDoors() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("doorTest", "Standard");
        assertEquals(new Position(1, 1), getEntityResponses(info, "player").get(0).getPosition());
        info.getEntities().forEach((e) -> System.out.println(e.getType()));
        assertEquals(new Position(1, 2), getEntityResponses(info, "key_1").get(0).getPosition());
        assertEquals(new Position(2, 1), getEntityResponses(info, "door_locked_1").get(0).getPosition());

        String save1 = getFileName(info.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(save1);
        controller.allGames().contains(save1);

        info = controller.loadGame(save1);
        assertEquals(new Position(1, 1), getEntityResponses(info, "player").get(0).getPosition());
        assertEquals(new Position(1, 2), getEntityResponses(info, "key_1").get(0).getPosition());
        assertEquals(new Position(2, 1), getEntityResponses(info, "door_locked_1").get(0).getPosition());

        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.UP);
        
        assertEquals(new Position(1, 1), getEntityResponses(info, "player").get(0).getPosition());
        assertEquals(0, getEntityResponses(info, "key_1").size());
        assertEquals(new Position(2, 1), getEntityResponses(info, "door_locked_1").get(0).getPosition());

        info = controller.tick(null, Direction.RIGHT);

        assertEquals(new Position(2, 1), getEntityResponses(info, "player").get(0).getPosition());
        assertEquals(0, getEntityResponses(info, "key_1").size());
        assertEquals(0, getEntityResponses(info, "door_locked_1").size());
        assertEquals(new Position(2, 1), getEntityResponses(info, "door_unlocked_1").get(0).getPosition());

        String save2 = getFileName(info.getDungeonName(), String.valueOf(Instant.now().toEpochMilli()));
        controller.saveGame(save2);
        controller.allGames().contains(save2);

        info = controller.loadGame(save2);
        assertEquals(new Position(2, 1), getEntityResponses(info, "player").get(0).getPosition());
        assertEquals(0, getEntityResponses(info, "key_1").size());
        assertEquals(0, getEntityResponses(info, "door_locked_1").size());
        assertEquals(new Position(2, 1), getEntityResponses(info, "door_unlocked_1").get(0).getPosition());
    }
}
