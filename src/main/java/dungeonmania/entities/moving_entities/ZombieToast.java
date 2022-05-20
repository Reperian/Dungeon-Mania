package dungeonmania.entities.moving_entities;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.util.BattleUtils;
import dungeonmania.movement.RandomMovement;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity implements Battleable {
    private final static String TYPE = "zombie_toast";
    private boolean alternate = false;
    public ZombieToast(Position position) {
        super(TYPE, position);
        setHealth(2);
        setDamage(10);
        movement = new RandomMovement();
        defaultMovement = movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        setMoved(Direction.NONE);
        movement.move(this);
        BattleUtils.checkBattlePlayer(this);
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
     * {@inheritDoc}
     */
    @Override
    public void init() {
    }

    @Override
    public List<AnimationQueue> getAnimationResponse() {
        alternate = !alternate;
        if (alternate) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite zombie_move"), false, -1));
        }
        else {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite zombie_toast"), false, -1));
        }
        return super.getAnimationResponse();
    }
    
}
