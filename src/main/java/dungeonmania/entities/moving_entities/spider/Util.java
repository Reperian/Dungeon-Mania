package dungeonmania.entities.moving_entities.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.door.LockedDoor;
import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wall.Wall;
import dungeonmania.util.Position;

public class Util {
    public static int getNumOfSpiders() {
        return EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Spider.class).size();
    }
    /**
     * Gets random positions between in the map by looking for a vacant square
     * will attempt to get this position for width*height amount of times
     * @postcondition position will be vacant and within map width and height
     * @return
     */
    public static Position getRandomValidPosition() {
        Position maxPosition = new Position(0, 0);
        Position minPosition = new Position(0, 0);
        List<Position> vacantPositions = new ArrayList<>();
        Set<Position> nonTraversable = new HashSet<>();
        for (Entity entity : Dungeon.getInstance().getEntities()) {
            Position position = new Position(entity.getPosition().getX(), entity.getPosition().getY());
            maxPosition = position.getX() > maxPosition.getX() ? new Position(position.getX(), maxPosition.getY()) : maxPosition;
            maxPosition = position.getY() > maxPosition.getY() ? new Position(maxPosition.getX(), position.getY()) : maxPosition;
            minPosition = position.getX() < minPosition.getX() ? new Position(position.getX(), minPosition.getY()) : minPosition;
            minPosition = position.getY() < minPosition.getY() ? new Position(minPosition.getX(), position.getY()) : minPosition;
            if (entity instanceof Wall || entity instanceof LockedDoor || entity instanceof Boulder || entity instanceof Player) {
                nonTraversable.add(position);
            }
        }
        for (int y = minPosition.getY(); y < maxPosition.getY(); y++) {
            for (int x = minPosition.getX(); x < maxPosition.getX(); x++) {
                vacantPositions.add(new Position(x,y));
            }
        }
        vacantPositions.removeAll(nonTraversable);
        if (vacantPositions.size() == 0) {
            return null;
        }
        else {
            return vacantPositions.get(Dungeon.getInstance().getRandom().nextInt(vacantPositions.size()));
        }
    }
}
