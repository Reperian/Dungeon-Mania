package dungeonmania.movement;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bomb.PlacedBomb;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.wall.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RunAwayMovement implements MovementStrategy {
    @Override
    //Moves away from the player going left or right before up or down
    public void move(Entity entity) {
        Player p = Dungeon.getInstance().getPlayer();
        Position compare = Position.calculatePositionBetween(p.getPosition(), entity.getPosition());
        if (leftRight(compare, entity) == false) {
            upDown(compare, entity);
        }
        else {
            return;
        }
        
    }
    //moves left or right
    public boolean leftRight(Position compare, Entity entity) {
        Position pos = entity.getPosition();
        if (compare.getX() < 0) {
            pos = pos.translateBy(Direction.LEFT);
        }
        else if (compare.getX() > 0) {
            pos = pos.translateBy(Direction.RIGHT);
        }

        if (compare.getY() == 0 || checkMove(pos) == false) {
            return false;
        }
        else {
            moveAnimation(entity, pos);
            entity.setPosition(pos);
            return true;
        }

    }
    //moves up or down
    public void upDown(Position compare, Entity entity) {
        Position pos = entity.getPosition();
        if (compare.getY() < 0) {
            pos = pos.translateBy(Direction.UP);
        }
        else if (compare.getY() > 0) {
            pos = pos.translateBy(Direction.DOWN);
        }

        if (compare.getY() == 0 || checkMove(pos) == false) {
            return;
        }
        moveAnimation(entity, pos);
        entity.setPosition(pos);
    }


    /**
     * @param position
     */
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
