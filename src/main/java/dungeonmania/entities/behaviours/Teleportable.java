package dungeonmania.entities.behaviours;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface Teleportable {
    default public Direction getDirectionEntered() {
        Position pos_vector = Position.calculatePositionBetween(getPrevPosition(), getCurrPosition());

        Direction dir = Direction.NONE;

        if (pos_vector.getX() < 0) {
            dir = Direction.LEFT;
        } else if (pos_vector.getX() > 0) {
            dir = Direction.RIGHT;
        }

        if (pos_vector.getY() < 0) {
            dir = Direction.UP;
        } else if (pos_vector.getY() > 0) {
            dir = Direction.DOWN;
        }

        return dir;
    };

    public Position getPrevPosition();

    public Position getCurrPosition();
}
