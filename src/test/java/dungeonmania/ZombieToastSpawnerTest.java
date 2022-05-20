package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawnerTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    private static List<ItemResponse> getItemResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getInventory().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }

    @Test
    public void testSpawnTickCountPeaceful(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("zombieToastSpawner", "Peaceful");

        List<EntityResponse> zombies = getEntityResponses(info, "zombie_toast");

        for (int i = 0; i < 20; i++) {
            zombies = getEntityResponses(info, "zombie_toast");
            assertTrue(zombies.size() == 0);

            info = controller.tick(null, Direction.UP);
        }
        zombies = getEntityResponses(info, "zombie_toast");
        assertTrue(zombies.size() == 1);
    }

    @Test
    public void testSpawnTickCountStandard(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("zombieToastSpawner", "Standard");
        
        List<EntityResponse> zombies = getEntityResponses(info, "zombie_toast");

        for (int i = 0; i < 20; i++) {
            zombies = getEntityResponses(info, "zombie_toast");
            assertTrue(zombies.size() == 0);

            info = controller.tick(null, Direction.UP);
        }
        zombies = getEntityResponses(info, "zombie_toast");
        assertTrue(zombies.size() == 1);
    }

    @Test
    public void testSpawnTickCountHard(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("zombieToastSpawner", "Hard");
        
        List<EntityResponse> zombies = getEntityResponses(info, "zombie_toast");

        for (int i = 0; i < 15; i++) {
            zombies = getEntityResponses(info, "zombie_toast");
            assertTrue(zombies.size() == 0);

            info = controller.tick(null, Direction.UP);
        }
        zombies = getEntityResponses(info, "zombie_toast");
        assertTrue(zombies.size() == 1);
    }

    @Test
    public void testSpawnLocation(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("zombieToastSpawner", "Standard");
        
        List<EntityResponse> zombies = getEntityResponses(info, "zombie_toast");
        EntityResponse spawner = getEntityResponses(info, "zombie_toast_spawner").get(0);

        for (int i = 0; i < 20; i++) {
            zombies = getEntityResponses(info, "zombie_toast");
            assertTrue(zombies.size() == 0);

            info = controller.tick(null, Direction.UP);
        }
        
        zombies = getEntityResponses(info, "zombie_toast");
        assertTrue(zombies.size() == 1);

        assertTrue(Position.isAdjacent(zombies.get(0).getPosition(), spawner.getPosition()));
    }

    @Test
    public void testPlayerDestroysSpawner(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("zombieToastSpawner", "Standard");

        EntityResponse spawner = getEntityResponses(info, "zombie_toast_spawner").get(0);

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.UP); 
        controller.tick(null, Direction.LEFT);
        info = controller.interact(spawner.getId());

        List<EntityResponse> spawners = getEntityResponses(info, "zombie_toast_spawner");
        assertFalse(spawners.contains(spawner));
    }

    @Test
    public void testPlayerHasNoWeapon(){
        
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse info = controller.newGame("zombieToastSpawner", "Standard");
            
            EntityResponse spawner = getEntityResponses(info, "zombie_toast_spawner").get(0);

            controller.tick(null, Direction.LEFT);
            info = controller.interact(spawner.getId());

            List<EntityResponse> spawners = getEntityResponses(info, "zombie_toast_spawner");
            assertTrue(spawners.contains(spawner));
        });  
    }

    @Test
    public void testPlayerIsNotAdjacent(){

        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse info = controller.newGame("zombieToastSpawner", "Standard");
            
            EntityResponse spawner = getEntityResponses(info, "zombie_toast_spawner").get(0);

            controller.tick(null, Direction.DOWN);
            controller.tick(null, Direction.UP); 
            controller.tick(null, Direction.RIGHT);
            info = controller.interact(spawner.getId()); 

            List<EntityResponse> spawners = getEntityResponses(info, "zombie_toast_spawner");
            assertTrue(spawners.contains(spawner));
        });
    }

    @Test
    public void testZombieSpawnWithArmour(){
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse info = controller.newGame("zombieToastSpawnWithArmour", "Standard", 79);
            for (int i = 0; i < 21; i++) {      
                info = controller.tick(null, Direction.DOWN);
            }
            for (int i = 0; i < 21; i++) {      
                info = controller.tick(null, Direction.LEFT);
            }
            List<ItemResponse> armour = getItemResponses(info, "armour");
            assertEquals(41, armour.size());
    }
}
