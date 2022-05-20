package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class BuildableEntitiesTests {

    @Test
    public void testBuildBow() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildBowShield", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        info = dungeon.build("bow");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("bow")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("arrow")));  
    }

    @Test
    public void testBuildShield1() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildBowShield", "Standard");

        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        
        info = dungeon.build("shield");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("shield")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("key"))); 
    }

    @Test
    public void testBuildShield2() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildBowShield", "Standard");

        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));

        info = dungeon.build("shield");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("shield")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
    }
    
    @Test
    public void testInvalidBuildable() {

        DungeonManiaController dungeon = new DungeonManiaController();
        dungeon.newGame("buildBowShield", "Standard");

        assertThrows(IllegalArgumentException.class, () -> {
             dungeon.build("invalid");
        });
    }

    @Test
    public void testInsufficientItems() {

        DungeonManiaController dungeon = new DungeonManiaController();
        dungeon.newGame("buildBowShield", "Standard");

        assertThrows(InvalidActionException.class, () -> {    
            dungeon.build("bow");
        });

        assertThrows(InvalidActionException.class, () -> {
            dungeon.build("shield");
        });   
    }

    @Test
    public void testBowShieldDurability() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildBowShield", "Standard");

        info = dungeon.tick(null, Direction.DOWN);
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("bow")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("shield")));
        info = dungeon.tick(null, Direction.DOWN);

        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("bow")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("shield")));
    }
}
