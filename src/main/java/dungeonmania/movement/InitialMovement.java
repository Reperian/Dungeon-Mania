package dungeonmania.movement;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class InitialMovement implements MovementStrategy {
    @Override
    //moves up one tick then switches movement to circular
    public void move(Entity entity) {
        final Position p = entity.getPosition().translateBy(Direction.UP);
        if (checkMove(p) == true) {
            moveAnimation(entity, p);
            entity.setPosition(entity.getPosition().translateBy(Direction.UP));
            Spider s = (Spider) entity;
            s.setStrategy(new CircularMovement());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkMove(Position position) {
        List<Entity> eList = Dungeon.getInstance().getEntities();
        List<Entity> entities = eList.stream().filter(e -> (e.getPosition().equals(position))).collect(Collectors.toList());

        for (Entity e : entities) {
            if (e instanceof Boulder) {
                return false;
            }
        }
        return true;
    }
}
