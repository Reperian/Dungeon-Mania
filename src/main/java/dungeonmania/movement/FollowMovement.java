package dungeonmania.movement;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bomb.PlacedBomb;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.door.LockedDoor;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wall.Wall;
import dungeonmania.movement.algorithms.Dijkstra;
import dungeonmania.util.Position;

public class FollowMovement implements MovementStrategy {
    @Override
    //Moves left or right first until it reaches desired x location then moves up or down
    public void move(Entity entity) {
        Dijkstra dijkstra = new Dijkstra(entity);
        Position nextPosition = dijkstra.getNextPosition(Dungeon.getInstance().getPlayer().getPosition());
        if (checkMove(nextPosition)) {
            moveAnimation(entity, nextPosition);
            entity.setPosition(nextPosition);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkMove(Position position) {
        for (Entity e : EntityUtils.getEntitiesAtPosition(position)) {
            if (e instanceof Wall || e instanceof Boulder || e instanceof LockedDoor || e instanceof PlacedBomb) {
                return false;
            }
        }
        return true;
    }
}
