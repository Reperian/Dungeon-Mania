package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.zombie_toast_spawner.ZombieToastSpawner;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class GoalsTest {

    @Test
    public void testSimpleTreasure() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("simpleTreasure", "Standard");

        assertTrue(info.getGoals().contains(":treasure (2)"));
        
        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        
        assertTrue(info.getGoals().contains("")); 
    }
    
    @Test
    public void testSimpleBoulders() {
        
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("simpleBoulders", "Standard");

        assertTrue(info.getGoals().contains(":boulders (2)"));
        
        info = dungeon.tick(null, Direction.RIGHT);

        assertTrue(info.getGoals().contains(":boulders (1)"));
        info = dungeon.tick(null, Direction.DOWN);
        
        assertTrue(info.getGoals().contains(""));  
    }

    @Test
    public void testSimpleEnemies() {
        
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("simpleEnemies", "Standard");

        assertTrue(info.getGoals().contains(":enemies (2)"));
        
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":enemies (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":enemies (1)"));

        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);

        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testSimpleExit() {
        
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("simpleExit", "Standard");

        assertTrue(info.getGoals().contains(":exit"));
        
        info = dungeon.tick(null, Direction.RIGHT);
        
        assertTrue(info.getGoals().contains(""));   
    }
    
    @Test
    public void testCompositeTreasureAndExit() {
        
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeTreasureAndExit", "Standard");

        assertTrue(info.getGoals().contains(":treasure (2) AND :exit"));
        
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":treasure (1) AND :exit"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit"));
        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":exit"));
        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":exit"));
        info = dungeon.tick(null, Direction.LEFT);
        
        assertTrue(info.getGoals().contains("")); 
    }
    
    @Test
    public void testCompositeEnemiesOrBoulders1() {
        
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":enemies (1) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":enemies (1) OR :boulders (1)"));
        
        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);

        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeEnemiesOrBoulders2() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.LEFT);

        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeExitandEnemiesOrBoulders1() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitAndEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit AND :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":exit"));
        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeExitandEnemiesOrBoulders2() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitAndEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit AND :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :enemies (1) OR :boulders (1)"));
        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);
        assertTrue(info.getGoals().contains(":exit"));
        info = dungeon.tick(null, Direction.DOWN);

        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeExitandEnemiesandBoulders() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitAndEnemiesAndBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit AND :enemies (2) AND :boulders (1)"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":enemies (2) AND :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :enemies (1) AND :boulders (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :enemies (1) AND :boulders (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :enemies (1) AND :boulders (1)"));
        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);
        assertTrue(info.getGoals().contains(":exit AND :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.DOWN);
        assertTrue(info.getGoals().contains(":exit"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeFourAndGoals() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitAndTreasureAndEnemiesAndBoulders", "Standard");
        assertTrue(info.getGoals().contains(":exit AND :treasure (2) AND :enemies (2) AND :boulders (1)"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":treasure (2) AND :enemies (2) AND :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :treasure (2) AND :enemies (1) AND :boulders (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit AND :treasure (2) AND :enemies (1) AND :boulders (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);
        assertTrue(info.getGoals().contains(":exit AND :treasure (2) AND :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        info = dungeon.tick(null, Direction.DOWN);
        assertTrue(info.getGoals().contains(":exit AND :treasure (2)"));

        info = dungeon.tick(null, Direction.LEFT);
        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(":exit"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeFourOrGoals1() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitOrTreasureOrEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (1) OR :boulders (1)"));
        info = dungeon.tick(null, Direction.RIGHT);
        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (1) OR :boulders (1)"));
        Dungeon d = Dungeon.getInstance();
        String zombie_toast_spawner_id = EntityUtils.getEntitiesOfTypeList(d.getEntities(), ZombieToastSpawner.class).get(0).getId();
        info = dungeon.interact(zombie_toast_spawner_id);
        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeFourOrGoals2() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitOrTreasureOrEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.DOWN);
        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeFourOrGoals3() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitOrTreasureOrEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.LEFT);
        assertTrue(info.getGoals().contains(""));
    }

    @Test
    public void testCompositeFourOrGoals4() {

        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("compositeExitOrTreasureOrEnemiesOrBoulders", "Standard");

        assertTrue(info.getGoals().contains(":exit OR :treasure (1) OR :enemies (2) OR :boulders (1)"));

        info = dungeon.tick(null, Direction.UP);
        assertTrue(info.getGoals().contains(""));
    }
}