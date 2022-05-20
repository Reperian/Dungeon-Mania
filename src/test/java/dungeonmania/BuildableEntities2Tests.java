package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import utils.TestUtil;

public class BuildableEntities2Tests {
    
    @Test
    public void testBuildSceptre1() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        
    }

    @Test
    public void testBuildSceptre2() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.DOWN);
        info = dungeon.tick(null, Direction.DOWN);
        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        
    }

    @Test
    public void testBuildSceptre3() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("arrow")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("arrow")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        
    }

    @Test
    public void testBuildSceptre4() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.UP);
        info = dungeon.tick(null, Direction.UP);
        info = dungeon.tick(null, Direction.UP);
        info = dungeon.tick(null, Direction.UP);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("arrow")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("arrow")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        
    }

    @Test
    public void testCollectSceptre() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));

        info = dungeon.tick(null, Direction.LEFT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        
    }

    @Test
    public void testBuildMidnightArmour() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildMidnightArmour", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("armour")));

        info = dungeon.build("midnight_armour");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("armour")));
        
    }

    @Test
    public void testBattleWithMidnightArmour() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildMidnightArmour", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("armour")));

        info = dungeon.build("midnight_armour");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("armour")));

        info = dungeon.tick(null, Direction.RIGHT);
        
    }

    @Test
    public void testMidnightArmourDurability() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildMidnightArmour", "Standard");

        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));

        info = dungeon.tick(null, Direction.DOWN);
        
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));
        
    }

    @Test
    public void testCollectMidnightArmour() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildMidnightArmour", "Standard");

        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));

        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("midnight_armour")));
        
    }

    @Test
    public void testInsufficientItems() {

        DungeonManiaController dungeon = new DungeonManiaController();
        dungeon.newGame("buildBowShield", "Standard");

        assertThrows(InvalidActionException.class, () -> {
            
            dungeon.build("sceptre");
        
        });

        assertThrows(InvalidActionException.class, () -> {
            
            dungeon.build("midnight_armour");
        
        });
        
    }

    @Test
    public void testZombiesPresentMidnightArmour() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildMidnightArmour", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        for (int i = 0; i < 5; i++) {

            info = dungeon.tick(null, Direction.RIGHT);
            info = dungeon.tick(null, Direction.DOWN);
            info = dungeon.tick(null, Direction.LEFT);
            info = dungeon.tick(null, Direction.UP);
            

        }

        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("armour")));

        assertThrows(InvalidActionException.class, () -> {
            
            dungeon.build("midnight_armour");
        
        });
        
    }

    @Test
    public void testSceptreBribeMercenary() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("treasure")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        assertEquals(0, TestUtil.getEntityResponses(info, "mercenary_friendly").size());
        assertEquals(1, TestUtil.getEntityResponses(info, "mercenary").size());
        
        EntityResponse mercenaryResp = TestUtil.getEntityResponses(info, "mercenary").get(0);
        info = dungeon.interact(mercenaryResp.getId());

        assertEquals(1, TestUtil.getEntityResponses(info, "mercenary_friendly").size());
        assertEquals(0, TestUtil.getEntityResponses(info, "mercenary").size());

        for (int i = 0; i < 9; i++) {
            info = dungeon.tick(null, Direction.RIGHT);
            assertEquals(1, TestUtil.getEntityResponses(info, "mercenary_friendly").size());
            assertEquals(0, TestUtil.getEntityResponses(info, "mercenary").size());

        }

        info = dungeon.tick(null, Direction.RIGHT);
        assertEquals(0, TestUtil.getEntityResponses(info, "mercenary_friendly").size());
        assertEquals(1, TestUtil.getEntityResponses(info, "mercenary").size());
        
    }

    @Test
    public void testSceptreBribeAssassin() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("buildSceptre", "Standard");

        info = dungeon.tick(null, Direction.DOWN);
        info = dungeon.tick(null, Direction.DOWN);
        info = dungeon.tick(null, Direction.DOWN);
        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        info = dungeon.build("sceptre");
        
        assertTrue(info.getInventory().stream().anyMatch(e -> e.getType().equals("sceptre")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("wood")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("key_1")));
        assertFalse(info.getInventory().stream().anyMatch(e -> e.getType().equals("sun_stone")));

        assertEquals(0, TestUtil.getEntityResponses(info, "assassin_friendly").size());
        assertEquals(1, TestUtil.getEntityResponses(info, "assassin").size());
        
        EntityResponse assassinResp = TestUtil.getEntityResponses(info, "assassin").get(0);
        info = dungeon.interact(assassinResp.getId());

        assertEquals(1, TestUtil.getEntityResponses(info, "assassin_friendly").size());
        assertEquals(0, TestUtil.getEntityResponses(info, "assassin").size());

        for (int i = 0; i < 9; i++) {
            info = dungeon.tick(null, Direction.DOWN);
            assertEquals(1, TestUtil.getEntityResponses(info, "assassin_friendly").size());
            assertEquals(0, TestUtil.getEntityResponses(info, "assassin").size());

        }

        info = dungeon.tick(null, Direction.DOWN);
        assertEquals(0, TestUtil.getEntityResponses(info, "assassin_friendly").size());
        assertEquals(1, TestUtil.getEntityResponses(info, "assassin").size());
        
    }
    

}
