package dungeonmania.entities.moving_entities;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Interactable;
import dungeonmania.entities.behaviours.Teleportable;
import dungeonmania.entities.moving_entities.util.BattleUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.sceptre.Sceptre;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.FollowMovement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity implements Battleable, Interactable, Teleportable {

    private final static String TYPE = "mercenary";
    private final static int MAX_HEALTH = 5;
    private Position prev_pos;

    public Mercenary(Position position) {
        super(TYPE, position);
        setHealth(MAX_HEALTH);
        setDamage(10);
        movement = new FollowMovement();
        defaultMovement = movement;
    }

    public Mercenary(String type, Position position) {
        super(type, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        setMoved(Direction.NONE);
        prev_pos = getPosition();
        movement.move(this);
        BattleUtils.checkBattlePlayer(this);
    }

    @Override
    public void onDeath() {
        Dungeon.getInstance().getPlayer().getInventory().addAll(getInventory());
        Dungeon.getInstance().getEntities().remove(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean interact() {
        Dungeon dungeon = Dungeon.getInstance();
        Player player = dungeon.getPlayer();
        List<Collectable> treasures = EntityUtils.getCollectablesFromInventory(player.getInventory(), Treasure.class);
        List<Collectable> sceptres = EntityUtils.getCollectablesFromInventory(player.getInventory(), Sceptre.class);
        if (treasures.size() > 0) {
            player.removeNumOfTypeFromInventory(1, Treasure.class);
            dungeon.removeEntity(this);
            FriendlyMercenary ally = new FriendlyMercenary(new Position(getPosition().getX(), getPosition().getY(), 2));
            dungeon.addEntity(ally);
            player.addAlly(ally);
            return true;
        } else if (sceptres.size() > 0) {
            player.removeNumOfTypeFromInventory(1, Sceptre.class);
            dungeon.removeEntity(this);
            FriendlyMercenary ally = new FriendlyMercenary(new Position(getPosition().getX(), getPosition().getY(), 2));
            ally.setFriendly_period(10);
            dungeon.addEntity(ally);
            player.addAlly(ally);
            return true;

        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        Dungeon.getInstance().getPlayer().battle(this);
        return true;         
    }
 
    /**
     * checks if the player is within the battle radius
     * @param p
     * @param entity
     * @return true if player in battle radius, false if not
     */
    public boolean checkBattleRadius(Player p) {
        List<Position> battleRadius = getPosition().getBattleRadius();
        List<Entity> eList = Dungeon.getInstance().getEntities();
        
        for (int i = 0; i < battleRadius.size(); i++) {
            Position pos = battleRadius.get(i);
            List<Entity> entities = eList.stream().filter(e -> (e.getPosition().equals(pos))).collect(Collectors.toList());
            if (entities.contains(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        
    }

    @Override
    public Position getPrevPosition() {
        return prev_pos;
    }

    @Override
    public Position getCurrPosition() {
        return getPosition();
    }
}
