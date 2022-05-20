package dungeonmania.entities.treasure;

import java.util.Arrays;
import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;

public class Treasure extends Entity implements Collectable {
    private static final String TYPE = "treasure";
    private int alternate = 0;

    /**
     * Constructor for the treasure
     * @param position
     */
    public Treasure(Position position) {
        super(TYPE, position);
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
    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
    }

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
    public boolean collect() {
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}

    @Override
    public List<AnimationQueue> getAnimationResponse() {
        if (alternate > 4) {
            alternate = 0;
        }
        if (alternate == 0) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite treasure"), false, -1));
        }
        else if (alternate == 1) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite treasure1"), false, -1));
        }
        else if (alternate == 2) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite treasure2"), false, -1));
        }
        else if (alternate == 3) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite treasure3"), false, -1));
        }
        else if (alternate == 4) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite treasure4"), false, -1));
        }
        alternate++;
        return super.getAnimationResponse();
    }
}
