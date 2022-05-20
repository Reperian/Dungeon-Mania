package dungeonmania.movement;

import dungeonmania.entities.Entity;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface MovementStrategy {
    /**
     * 
     * @param entity
     */
    public void move(Entity entity);

    /**
     * 
     * @param position
     * @return
     */
    public boolean checkMove(Position position);

    public default void moveAnimation(Entity entity, Position newPos) {
        Position position = Position.calculatePositionBetween(entity.getPosition(), newPos);
        MovingEntity m = (MovingEntity) entity;
        if (position.getX() == -1) {
            m.setMoved(Direction.LEFT);
        }
        if (position.getX() == 1) {
            m.setMoved(Direction.RIGHT);
        }
        if (position.getY() == -1) {
            m.setMoved(Direction.UP);
        } 
        if (position.getY() == 1) {
            m.setMoved(Direction.DOWN);
        }   
    }


}
