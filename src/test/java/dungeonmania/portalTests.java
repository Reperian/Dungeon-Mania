package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class portalTests {

    private static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
    
    @Test
    public void testPortalZombieNoEffect() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_zombie", "Standard", 5);
    
        EntityResponse zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(1,1), zombie.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        zombie = getEntityResponses(info, "zombie_toast").get(0);
        assertEquals(new Position(2,1), zombie.getPosition()); 
    }

    @Test
    public void testPortalSpiderNoEffect() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_spider", "Standard", 5);
    
        EntityResponse spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,2), spider.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        spider = getEntityResponses(info, "spider").get(0);
        assertEquals(new Position(1,1), spider.getPosition()); 
    }

    @Test
    public void testPortalHydraNoEffect() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_hydra", "Standard", 5);
    
        EntityResponse hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(1,1), hydra.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        hydra = getEntityResponses(info, "hydra").get(0);
        assertEquals(new Position(2,1), hydra.getPosition()); 

    }

    @Test
    public void testOnePortalRightEntry() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals", "Standard", 5);
        
        EntityResponse player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0,0), player.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(5,0), player.getPosition());
    }

    @Test
    public void testOnePortalLeftEntry(){
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals", "Standard", 5);

        EntityResponse player = getEntityResponses(info, "player").get(0);

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        
        info = controller.tick(null, Direction.UP);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(5,0), player.getPosition());

        info =  controller.tick(null, Direction.LEFT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0,0), player.getPosition());
    }

    @Test
    public void testOnePortalUpEntry(){
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals", "Standard", 5);

        EntityResponse player = getEntityResponses(info, "player").get(0);

        controller.tick(null, Direction.DOWN);
        
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,1), player.getPosition());

        info = controller.tick(null, Direction.UP);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(4,-1), player.getPosition());
    }

    @Test
    public void testOnePortalDownEntry(){
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals", "Standard", 5);
        EntityResponse player = getEntityResponses(info, "player").get(0);

        controller.tick(null, Direction.UP);
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,-1), player.getPosition());

        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(4,1), player.getPosition());
    }

    @Test
    public void testPortalBlocked(){
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("blocked_portal", "Standard", 5);
        EntityResponse player = getEntityResponses(info, "player").get(0);
        
        assertEquals(new Position(0,0), player.getPosition());

        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0, 0), player.getPosition());
    }

    @Test
    public void testTwoPortals(){
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("two_portals", "Standard", 5);

        EntityResponse player = getEntityResponses(info, "player").get(0);

        assertEquals(new Position(0,0), player.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(5,0), player.getPosition());
        
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(5,2), player.getPosition());
        info = controller.tick(null, Direction.LEFT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(0,2), player.getPosition());

        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.UP);
        info = controller.tick(null, Direction.RIGHT);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,-1), player.getPosition());
        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(4,1), player.getPosition());

        info = controller.tick(null, Direction.DOWN);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,3), player.getPosition());

        info = controller.tick(null, Direction.UP);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(4,1), player.getPosition());

        info = controller.tick(null, Direction.UP);
        player = getEntityResponses(info, "player").get(0);
        assertEquals(new Position(1,-1), player.getPosition());
    }

    @Test
    public void testPortalMercenaryRight() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc", "Standard", 5);
        
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
        
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(6,1), merc.getPosition());
    }

    @Test
    public void testPortalMercenaryUp() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc", "Standard", 5);
        
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.UP);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(2,0), merc.getPosition());
    }

    @Test
    public void testPortalMercenaryDown() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(0,1), merc.getPosition());
        
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.DOWN);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(5,2), merc.getPosition());
    }

    @Test
    public void testPortalMercenaryLeft() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(6,1), merc.getPosition());
        info = controller.tick(null, Direction.LEFT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
    }

    @Test
    public void testPortalMercenaryBlocked() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("blocked_portal_merc", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        info = controller.tick(null, Direction.DOWN);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
    }

    @Test
    public void testPortalFriendlyMercenaryRight() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc_friendly", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(6,1), merc.getPosition());
    }

    @Test
    public void testPortalFriendlyMercenaryUp() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc_friendly", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);
        info = controller.tick(null, Direction.UP);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(5,0), merc.getPosition());
    }

    @Test
    public void testPortalFriendlyMercenaryDown() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc_friendly", "Standard", 3);
        
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.DOWN);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(5,2), merc.getPosition());
    }

    @Test
    public void testPortalFriendlyMercenaryLeft() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_merc_friendly", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);
        
        assertEquals(new Position(0,1), merc.getPosition());
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(6,1), merc.getPosition());
        info = controller.tick(null, Direction.LEFT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
    }

    @Test
    public void testPortalFriendlyMercenaryBlocked() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("blocked_portal_merc_friendly", "Standard");
        
        EntityResponse merc = getEntityResponses(info, "mercenary_friendly").get(0);

        assertEquals(new Position(0,1), merc.getPosition());
        info = controller.tick(null, Direction.DOWN);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        merc = getEntityResponses(info, "mercenary_friendly").get(0);
        assertEquals(new Position(1,1), merc.getPosition());
    }

    @Test
    public void testPortalAssassinRight() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info =  controller.newGame("portals_assassin", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(6,1), assassin.getPosition());
    }

    @Test
    public void testPortalAssassinUp() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);
        
        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        info = controller.tick(null, Direction.UP);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(2,2), assassin.getPosition());
    }

    @Test
    public void testPortalAssassinDown() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.UP);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(2,0), assassin.getPosition());
    }

    @Test
    public void testPortalAssassinLeft() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);
        
        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(6,1), assassin.getPosition());
        info = controller.tick(null, Direction.LEFT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
    }

    @Test
    public void testPortalAssassinBlocked() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("blocked_portal_assassin", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        info = controller.tick(null, Direction.DOWN);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
    }

    @Test
    public void testPortalFriendlyAssassinRight() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin_friendly", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);
        
        assertEquals(new Position(0,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(6,1), assassin.getPosition());
    }

    @Test
    public void testPortalFriendlyAssassinUp() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin_friendly", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.UP);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(2,0), assassin.getPosition());
    }

    @Test
    public void testPortalFriendlyAssassinDown() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin_friendly", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.UP);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        info = controller.tick(null, Direction.DOWN);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(5,2), assassin.getPosition());
    }

    @Test
    public void testPortalFriendlyAssassinLeft() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("portals_assassin_friendly", "Standard");
        
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        controller.tick(null, Direction.DOWN);
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(6,1), assassin.getPosition());
        info = controller.tick(null, Direction.LEFT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
    }

    @Test
    public void testPortalFriendlyAssassinBlocked() {
        
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse info = controller.newGame("blocked_portal_assassin_friendly", "Standard", 5);
        
        EntityResponse assassin = getEntityResponses(info, "assassin_friendly").get(0);

        assertEquals(new Position(0,1), assassin.getPosition());
        info = controller.tick(null, Direction.DOWN);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
        info = controller.tick(null, Direction.RIGHT);
        assassin = getEntityResponses(info, "assassin_friendly").get(0);
        assertEquals(new Position(1,1), assassin.getPosition());
    }
}