package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTileTest {
    
    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    private static List<ItemResponse> getItemResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getInventory().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
    public void testPlayerNoEffect_Straight(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTilePlayer", "Standard");
    
        EntityResponse player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0,0), player.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,0), player.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(2,0), player.getPosition()); 
    }

    @Test
    public void testPlayerNoEffect_Turn(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTilePlayer", "Standard");
    
        EntityResponse player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0,0), player.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,0), player.getPosition()); 
        
        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,1), player.getPosition()); 
    }

    @Test
    public void testMercenaryThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileMercenary", "Standard");
    
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1,2), merc.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(2,1,2), merc.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(2,1,2), merc.getPosition());  
        
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(3,1), merc.getPosition());
    }

    @Test
    public void testMercenaryThroughTile_MF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileMercenaryMF10", "Standard");
    
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1), merc.getPosition());

        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            merc = getEntityResponses(info, "mercenary").get(0);
            assertEquals(new Position(2,1), merc.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(3,1), merc.getPosition()); 
    }

    @Test
    public void testFriendlyMercenaryThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileFriendlyMercenary", "Standard");
    
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(2,1), merc.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(2,1), merc.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(3,1), merc.getPosition()); 
    }

    @Test
    public void testFriendlyMercenaryThroughTile_MF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileFriendlyMercenaryMF10", "Standard");
    
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());

        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            merc = getEntityResponses(info, "mercenary_friendly").get(0);
            assertEquals(new Position(2,1), merc.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(3,1), merc.getPosition()); 
    }

    @Test
    public void testAssassinThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileAssassin", "Standard");
    
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(2,1), assassin.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(2,1), assassin.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(3,1), assassin.getPosition()); 
    }

    @Test
    public void testAssassinThroughTile_MF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileAssassinMF10", "Standard");
    
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());

        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            assassin = getEntityResponses(info, "assassin").get(0);
            assertEquals(new Position(2,1), assassin.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(3,1), assassin.getPosition()); 
    }

    @Test
    public void testAssassinFriendlyThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileFriendlyAssassin", "Standard");
    
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(2,1), assassin.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(2,1), assassin.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(3,1), assassin.getPosition()); 
    }

    @Test
    public void testAssassinFriendlyThroughTile_MF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileFriendlyAssassinMF10", "Standard");
    
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());

        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            assassin = getEntityResponses(info, "assassin_friendly").get(0);
            assertEquals(new Position(2,1), assassin.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(3,1), assassin.getPosition()); 
    }

    @Test
    public void testSpiderThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileSpider", "Standard");
    
        EntityResponse spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,1), spider.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,0), spider.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,0), spider.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(2,0), spider.getPosition()); 
    }

    @Test
    public void testSpiderThroughTileMF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileSpiderMF10", "Standard");
    
        EntityResponse spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,1), spider.getPosition());
    
        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            spider = getEntityResponses(info, "spider").get(0);
            assertEquals(new Position(1,0), spider.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(2,0), spider.getPosition()); 
    }

    @Test
    public void testZombieThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileZombie", "Standard");
    
        EntityResponse zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(1,1), zombie.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(2,1), zombie.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(2,1), zombie.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertNotEquals(new Position(2,1), zombie.getPosition()); 
    }

    @Test
    public void testZombieThroughTileMF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileZombieMF10", "Standard");
    
        EntityResponse zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(1,1), zombie.getPosition());
    
        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            zombie = getEntityResponses(info, "zombie_toast").get(0);
            assertEquals(new Position(2,1), zombie.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertNotEquals(new Position(2,1), zombie.getPosition()); 
    }

    @Test
    public void testHydraThroughTile(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileHydra", "Standard");
    
        EntityResponse hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(1,1), hydra.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(2,1), hydra.getPosition()); 

        info = controller.tick(null, Direction.RIGHT);
        hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(2,1), hydra.getPosition()); 
        
        info = controller.tick(null, Direction.RIGHT);
        hydra = getEntityResponses(info, "hydra").get(0);
        assertNotEquals(new Position(2,1), hydra.getPosition()); 
    }

    @Test
    public void testHydraThroughTileMF10(){
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("swampTileHydraMF10", "Standard");
    
        EntityResponse hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(1,1), hydra.getPosition());
    
        for (int i = 0; i < 10; i++) {
            info = controller.tick(null, Direction.RIGHT);
            hydra = getEntityResponses(info, "hydra").get(0);
            assertEquals(new Position(2,1), hydra.getPosition()); 
        }
        
        info = controller.tick(null, Direction.RIGHT);
        hydra = getEntityResponses(info, "hydra").get(0);
        assertNotEquals(new Position(2,1), hydra.getPosition()); 
    }

    @Test
    public void TestMercenaryRunAwaySwampTileDuringInvincibilityState(){

        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("MercenarySwampTileInvincible", "Standard");
    
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);
        String invincibility_potion = getItemResponses(info, "invincibility_potion").get(0).getId();

        info = controller.tick(null, Direction.RIGHT);
        
        info = controller.tick(null, Direction.RIGHT);
        info = controller.tick(invincibility_potion, Direction.NONE);
        info = controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.LEFT);

        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(3,1), merc.getPosition());

        info = controller.tick(null, Direction.UP);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(2,1), merc.getPosition());
    }
}
