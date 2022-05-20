package dungeonmania.movement;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bomb.PlacedBomb;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.wall.Wall;
import dungeonmania.util.Position;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomMovement implements MovementStrategy {
    
    /**
     * {@inheritDoc}
     */
    @Override
    //Moves in a random valid direction up, right, down, left
    public void move(Entity entity) {
        Random rand = Dungeon.getInstance().getRandom();
        //gets adjacent four positions
        List<Position> adjacent = entity.getPosition().getAdjacentFour();
        List<Position> found = new ArrayList<>();
        //checks if adjacent positions are valid
        for (Position p : adjacent) {
            if (checkMove(p) == false) {
                found.add(p);
            }
        }
        //removes invalid positions from list
        adjacent.removeAll(found);
        if (adjacent.isEmpty()) {
            return;
        }
        //randomly moves to a valid position
        int num = rand.nextInt(adjacent.size());
        Position newPos = adjacent.get(num);
        moveAnimation(entity, newPos);
        entity.setPosition(adjacent.get(num));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkMove(Position position) {
        final Position p = position;
        List<Entity> eList = Dungeon.getInstance().getEntities();
        //gets entity at position entity will be moving to
        List<Entity> entities = eList.stream().filter(e -> (e.getPosition().equals(p))).collect(Collectors.toList());
        for (Entity e : entities) {
            if (e instanceof Wall || e instanceof Boulder || e instanceof PlacedBomb) {
                return false;
            }
        }
        return true;
    }
}
