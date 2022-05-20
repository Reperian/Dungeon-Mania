package dungeonmania.entities.anduril;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.moving_entities.Assassin;
import dungeonmania.entities.moving_entities.Hydra;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

//The mighty sword once wielded by the legendary hero Andurius. It has been said to be forged from the flesh of un-countable sacrificed zombie toasts. It has the power to seal the regenerative powers of the hydras.
//It now sits inside a dungeon with a 10% drop rate, awaiting the return of a hero worthy enough to wield it's power once more and rid the land of the evil hydras.
public class Anduril extends Entity implements Collectable, Weapon  {
    private static final String TYPE = "anduril";
    public static final float DAMAGE = 2;
    public static final int HIT_MULTIPLIER = 1;
    public static final int MAGIC_POWER = 3;

    /**
     * Constructor for an anduril
     * @param position
     */
    public Anduril(Position position) {
        super(TYPE, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean collect() {
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return collect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void reduceDurability(int amount) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public float getDamage() {
        return DAMAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHitMultiplier() {
        return HIT_MULTIPLIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEntityTypeMultiplier(Entity entity) {
        if (entity instanceof Hydra || entity instanceof Assassin) {
            return MAGIC_POWER;
        }
        return 1;
    }
}
