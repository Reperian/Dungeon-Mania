package dungeonmania.entities.moving_entities;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.player.Player;
import dungeonmania.movement.FollowMovement;
import dungeonmania.util.Position;

public class FriendlyAssassin extends FriendlyMercenary {

    private final static String TYPE = "assassin_friendly";
    private final static int MAX_HEALTH = 5;

    public FriendlyAssassin(Position position) {
        super(TYPE, position);
        setHealth(MAX_HEALTH);
        setDamage(20);
        movement = new FollowMovement();
        defaultMovement = movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void friendly_period_tick() {
        
        setFriendly_period(getFriendly_period() -1);

        if (getFriendly_period() <= 0) {

            Dungeon dungeon = Dungeon.getInstance();
            Player player = dungeon.getPlayer();

            dungeon.removeEntity(this);
            player.removeAlly(this);
            Assassin enemy = new Assassin(new Position(getPosition().getX(), getPosition().getY(), 2));
            dungeon.addEntity(enemy); 

        }

    }
    
}
