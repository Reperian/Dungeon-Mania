package dungeonmania.entities.moving_entities;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.sceptre.Sceptre;
import dungeonmania.entities.the_one_ring.TheOneRing;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.FollowMovement;
import dungeonmania.util.Position;

public class Assassin extends Mercenary {

    private final static String TYPE = "assassin";
    private final static int MAX_HEALTH = 5;

    public Assassin(Position position) {
        super(TYPE, position);
        setHealth(MAX_HEALTH);
        setDamage(20);
        movement = new FollowMovement();
        defaultMovement = movement;
    }

    @Override
    //assassins turn into friendly assassins if interacted with treasure and a one ring in inventory
    public boolean interact() {
        Dungeon dungeon = Dungeon.getInstance();
        Player player = dungeon.getPlayer();
        List<Collectable> treasures = EntityUtils.getCollectablesFromInventory(player.getInventory(), Treasure.class);
        List<Collectable> oneRing = EntityUtils.getCollectablesFromInventory(player.getInventory(), TheOneRing.class);
        List<Collectable> sceptres = EntityUtils.getCollectablesFromInventory(player.getInventory(), Sceptre.class);
        if (treasures.size() > 0 && oneRing.size() > 0) {
            player.removeNumOfTypeFromInventory(1, Treasure.class);
            player.removeNumOfTypeFromInventory(1, TheOneRing.class);
            dungeon.removeEntity(this);
            FriendlyAssassin ally = new FriendlyAssassin(new Position(getPosition().getX(), getPosition().getY(), 2));
            dungeon.addEntity(ally);
            player.addAlly(ally);
            return true;
        } else if (sceptres.size() > 0) {
            player.removeNumOfTypeFromInventory(1, Sceptre.class);
            dungeon.removeEntity(this);
            FriendlyAssassin ally = new FriendlyAssassin(new Position(getPosition().getX(), getPosition().getY(), 2));
            ally.setFriendly_period(10);
            dungeon.addEntity(ally);
            player.addAlly(ally);
            return true;

        }
        return false;
    }
    
}
