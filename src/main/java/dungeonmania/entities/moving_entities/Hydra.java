package dungeonmania.entities.moving_entities;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.anduril.Anduril;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.util.BattleUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.RandomMovement;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

//Born from the remnants of the Eldritch evil that once plagued the land, these beings possess unfathomable regenerative properties.
//By absorbing the negative energies from it's surroundings, they are capable of restoring limbs in the matter of seconds.
//To slay such monstrosities, one must possess one of two things: a sword forged from the flesh of zombie toasts, or the ability to strike faster than the eye can see.
public class Hydra extends MovingEntity implements Battleable {
    private final static String TYPE = "hydra";
    private final static int MAX_HEALTH = 5;
    private boolean alternate = false;

    public Hydra(Position position) {
        super(TYPE, position);
        setHealth(MAX_HEALTH);
        setDamage(5);
        movement = new RandomMovement();
        defaultMovement = movement;
    }

    @Override
    //damage multiplier becomes negative at a 50% chance, therefore healing the hydra at the battle phase
    //if player has "Anduril, Flame of The West" in their inventory, Hydras' regenerative abilities are sealed because of the sword's legendary power.
    public float damageMultiplier() {
        Player p = Dungeon.getInstance().getPlayer();
        if (EntityUtils.getCollectablesFromInventory(p.getInventory(), Anduril.class).size() > 0) {
            return 1;
        }
        else {
            return Dungeon.getInstance().getRandom().nextBoolean() ? -1 : 1;
        }
    }

    @Override
    public void onTick() {
        setMoved(Direction.NONE);
        movement.move(this);
        BattleUtils.checkBattlePlayer(this);
    }

    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        Dungeon.getInstance().getPlayer().battle(this);
        return true; 
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<AnimationQueue> getAnimationResponse() {
        alternate = !alternate;
        if (alternate) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite hydra_move"), false, -1));
        }
        else {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite hydra"), false, -1));
        }
        return super.getAnimationResponse();
    }

    
}
