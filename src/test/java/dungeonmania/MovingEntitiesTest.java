package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import utils.TestUtil;


public class MovingEntitiesTest {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
    public void testPlayerMovement() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("playerTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "player");
        
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.LEFT);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.DOWN);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
    }

    @Test
    public void testPlayerMoveIntoWall() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("playerTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "player");
        
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "player");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
    }

    @Test
    public void testSpiderMovement() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("spiderTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "spider");

        assertEquals(new Position(5,5), list.get(0).getPosition());
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 6 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 6 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 6 && list.get(0).getPosition().getY() == 6);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 6);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 6);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
    }

    @Test
    public void testSpiderWhenObstructed () {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("spiderObstructedSpawn", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "spider");
        
        assertTrue(info.getEntities().size() == 3);
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
    }

    @Test
    public void testSpiderMovementBoulder () {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("spiderBoulderTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "spider");
        
        assertTrue(info.getEntities().size() == 3);
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 4 && list.get(0).getPosition().getY() == 4);
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(4,5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(4,6), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(5,6), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(6,6), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(6,5), list.get(0).getPosition());
        
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(6,5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertEquals(new Position(6,6), list.get(0).getPosition());
    }

    @Test
    public void testZombieMovement() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("zombieTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "zombie_toast");
        
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);

        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "zombie_toast");
        Position current = list.get(0).getPosition();
        assertTrue(current.getX() == 6 && current.getY() == 5);
        
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "zombie_toast");
        assertTrue(list.get(0).getPosition() != current);
        assertTrue(current != list.get(0).getPosition());
    }

    @Test
    public void testMercenaryMovement() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("advanced", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "mercenary");
        assertEquals(1, TestUtil.getEntityResponses(info, "mercenary").size());

        assertEquals(new Position(3,5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(3,4), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,4), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,3), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,2), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,1), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(3,1), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(4,1), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(5,1), list.get(0).getPosition());
    }

    @Test
    public void testMercenaryMovement2() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("advanced", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "mercenary");
        assertEquals(1, TestUtil.getEntityResponses(info, "mercenary").size());

        assertEquals(new Position(3,5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(3,4), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,4), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,3), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.UP);
        list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(2,2), list.get(0).getPosition());
    }

    @Test
    public void testBattle() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("spiderObstructedSpawn", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "spider");
        assertTrue(list.isEmpty() == false);

        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "spider");
        assertTrue(list.isEmpty() == true);
        assertEquals(Dungeon.getInstance().getPlayer().getHealth(), 19);
    }

    @Test
    public void testBattleRadius() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("mercenaryTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "mercenary");
        assertTrue(list.get(0).getPosition().getX() == 5 && list.get(0).getPosition().getY() == 5);
        
        info = dungeon.tick(null, Direction.LEFT);
        list = getEntityResponses(info, "mercenary");
        assertTrue(list.get(0).getPosition().getX() == 3 && list.get(0).getPosition().getY() == 5);
        assertEquals(Dungeon.getInstance().getPlayer().getHealth(), 19);
    }

    @Test
    public void testFriendlyMercenary() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("friendlyMercenaryTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "mercenary_friendly");
        assertEquals(new Position(5, 5), list.get(0).getPosition());

        info = dungeon.tick(null, Direction.LEFT);
        list = getEntityResponses(info, "mercenary_friendly");
        assertEquals(new Position(4, 5), list.get(0).getPosition());
        assertEquals(Dungeon.getInstance().getPlayer().getHealth(), 20);
        info = dungeon.tick(null, Direction.DOWN);
        list = getEntityResponses(info, "mercenary_friendly");
        assertEquals(new Position(3, 5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.DOWN);
        list = getEntityResponses(info, "mercenary_friendly");
        assertEquals(new Position(3, 6), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.DOWN);
        list = getEntityResponses(info, "mercenary_friendly");
        assertEquals(new Position(3, 7), list.get(0).getPosition());
    }

    @Test
    public void testRunAway() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("invincibleTest2", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "spider");
        String PotID = TestUtil.getEntityResponses(info, "invincibility_potion").get(0).getId();
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 2 && list.get(0).getPosition().getY() == 5);

        info = dungeon.tick(null, Direction.LEFT);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 2 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(PotID, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 1 && list.get(0).getPosition().getY() == 5);
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 1 && list.get(0).getPosition().getY() == 6);
        info = dungeon.tick(null, Direction.NONE);
        list = getEntityResponses(info, "spider");
        assertTrue(list.get(0).getPosition().getX() == 1 && list.get(0).getPosition().getY() == 6);
    }

    @Test
    public void mercenaryArmour() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("EnemiesWithArmorTest", "Standard");
        List<EntityResponse> list = getEntityResponses(info, "mercenary");
        assertEquals(new Position(4,5), list.get(0).getPosition());
        info = dungeon.tick(null, Direction.RIGHT);
        list = getEntityResponses(info, "mercenary");
        assertTrue(list.size() == 0);
        list = getEntityResponses(info, "armour");
        assertEquals(1, info.getInventory().size());
    }
    
    @Test
    public void mercenarySpawn() {
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse info = dungeon.newGame("protected_player", "Standard", 2);
        assertEquals(0, getEntityResponses(info, "mercenary").size());
        info = dungeon.tick(null, Direction.RIGHT);
        for (int i = 0; i < 49; i++) {
            info = dungeon.tick(null, Direction.NONE);
        }
        assertEquals(1, getEntityResponses(info, "mercenary").size());
    }

    @Test
    public void testMercenaryAllyBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("multipleEnemiesOneSquare", "Standard");
        
        List<EntityResponse> ally_merc = getEntityResponses(info, "mercenary_friendly");
        assertEquals(1, ally_merc.size());

        List<EntityResponse> enemies = getEntityResponses(info, "spider");
        enemies.addAll(getEntityResponses(info, "mercenary"));
        assertEquals(7, enemies.size());
       
        info = controller.tick(null, Direction.RIGHT);
        ally_merc = getEntityResponses(info, "mercenary_friendly");
        assertEquals(0, ally_merc.size());

        enemies = getEntityResponses(info, "spider");
        enemies.addAll(getEntityResponses(info, "mercenary"));
        assertEquals(0, enemies.size());

        List<EntityResponse> player = getEntityResponses(info, "player");
        assertEquals(1, player.size());
    }
}
