package dungeonmania.movement;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.util.Position;

public class CircularMovement implements MovementStrategy {

    private int increment = 1;
    private int initialPosition = 1;

    public CircularMovement () {}

    /**
     * {@inheritDoc}
     */
    @Override
    //Moves in a circular pattern clockwise
    public void move(Entity entity) {
        Spider s = (Spider) entity;
        List<Position> adjacent = s.getAdjacent();

        //increments the position
        // 0 1 2
        // 7 p 3
        // 6 5 4
        int newPosition = initialPosition + increment;

        //bounds position between 0-7
        if (newPosition > 7) {
            newPosition -= 8;
        }
        else if (newPosition < 0) {
            newPosition += 8;
        }
        //if encounters boulder, movement changes direction, clockwise to anti-clockwise vice versa
        final Position p = adjacent.get(newPosition);
        if (checkMove(p) == false) {
            increment = increment * -1;
            return;
        }
        moveAnimation(entity, p);
        entity.setPosition(p);
        initialPosition = newPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkMove(Position position) {
        List<Entity> eList = Dungeon.getInstance().getEntities();
        List<Entity> entities = eList.stream().filter(e -> (e.getPosition().equals(position))).collect(Collectors.toList());

        //check if boulder will obstruct spider
        for (Entity e : entities) {
            if (e instanceof Boulder) {
                return false;
            }
        }
        return true;
    }
}
