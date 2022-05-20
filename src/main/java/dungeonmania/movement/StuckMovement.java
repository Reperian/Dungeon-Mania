package dungeonmania.movement;

import dungeonmania.entities.Entity;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.util.Position;

public class StuckMovement implements MovementStrategy{

    private int ticks_til_unstuck;

    /* 
    * Constructor for StuckMovement strategy
    */
    public StuckMovement(int ticks_til_unstuck) {
        this.ticks_til_unstuck = ticks_til_unstuck;
    }

    /** 
     * {@inheritDoc}
    */
    @Override
    public void move(Entity entity) {
        MovingEntity moving_entity = (MovingEntity) entity;

        MovementStrategy default_movement = moving_entity.getDefaultMovementStrategy();

        Position pos = entity.getPosition();

        if (checkMove(pos)) {
            // What if it needs to be runaway not defualt?????
            if (ticks_til_unstuck == 1) {
                moving_entity.setMovementStrategy(default_movement);
            }
            if (ticks_til_unstuck == 0) {
                moving_entity.getMovementStrategy().move(moving_entity);
            }
        } 
    }

    /** 
     * {@inheritDoc}
    */
    @Override
    public boolean checkMove(Position position) {
        if (ticks_til_unstuck <=1) {
            return true;
        } 
        ticks_til_unstuck--;
        return false;
    }

    @Override
    public void moveAnimation(Entity entity, Position newPos) {
        // TODO Auto-generated method stub
        
    }
}
