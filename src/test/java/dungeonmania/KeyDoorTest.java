package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class KeyDoorTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
    public void testDoorLockedNoKey(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("doorTest", "Standard");

        EntityResponse player = getEntityResponses(info, "player").get(0);

        assertEquals(new Position(1,1), player.getPosition());
        
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,1), player.getPosition()); 
    }

    @Test
    public void testDoorUnlockedWithKey(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("doorTest", "Standard");

        EntityResponse player = getEntityResponses(info, "player").get(0);

        assertEquals(new Position(1,1), player.getPosition());
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP);
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(2,1), player.getPosition()); 
    }
    
    @Test
    public void testDoorWrongKey(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("twoDoorTest", "Standard");

        EntityResponse player = getEntityResponses(info, "player").get(0);

        controller.tick(null, Direction.UP); 
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,2), player.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,2), player.getPosition()); 
    }

    @Test
    public void testTwoDoorsRightKeys(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("twoDoorTest", "Standard");

        EntityResponse player = getEntityResponses(info, "player").get(0);

        controller.tick(null, Direction.UP); 
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,0), player.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(2,0), player.getPosition()); 

        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN); 
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,2), player.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(2,2), player.getPosition()); 
    }
}