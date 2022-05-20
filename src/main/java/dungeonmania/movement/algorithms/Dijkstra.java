package dungeonmania.movement.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.bomb.PlacedBomb;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.door.LockedDoor;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.swamp_tile.SwampTile;
import dungeonmania.entities.unimplemented.UnimplementedEntity;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wall.Wall;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

public class Dijkstra {
    private static final int OFFSET = 5;
    private Entity entity;

    private Map<Position, Double> dist = new HashMap<>();
    private Map<Position, Position> prev = new HashMap<>();
    private List<Position> allPositions;
    private Map<Position, Integer> costs = new HashMap<>();
    private Position minPosition = new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
    private Position maxPosition = new Position(Integer.MIN_VALUE, Integer.MIN_VALUE);
    

    public Dijkstra (Entity entity) {
        this.entity = entity;
        
        updateSelf();
        for (Position position : allPositions) {
            dist.put(position, Double.POSITIVE_INFINITY);
            prev.put(position, null);
        }
        algorithm(entity.getPosition());
    }
    /**
     * Updates the locations of all entities for the algorithm
     */
    private void updateSelf() {
        this.allPositions = new ArrayList<>();
        Set<Position> nonTraverable = new HashSet<>();
        Position playerPos = null;
        // Gets a list of entities to remove from the traversable list
        for (Entity entity : Dungeon.getInstance().getEntities()) {
            Position position = new Position(entity.getPosition().getX(), entity.getPosition().getY());
            if (entity instanceof Player) {
                playerPos = position;
            }
            // Set new bounds for the map
            maxPosition = position.getX() > maxPosition.getX() ? new Position(position.getX(), maxPosition.getY()) : maxPosition;
            maxPosition = position.getY() > maxPosition.getY() ? new Position(maxPosition.getX(), position.getY()) : maxPosition;
            minPosition = position.getX() < minPosition.getX() ? new Position(position.getX(), minPosition.getY()) : minPosition;
            minPosition = position.getY() < minPosition.getY() ? new Position(minPosition.getX(), position.getY()) : minPosition;
            if (entity instanceof Wall || entity instanceof LockedDoor || entity instanceof Boulder || entity instanceof PlacedBomb) {
                nonTraverable.add(position);
                continue;
            }
            costs.put(position, getCost(position));
        }
        // Makes the player trackable when inside another entity
        nonTraverable.remove(playerPos);
        for (int y = minPosition.getY() - OFFSET; y < maxPosition.getY() + OFFSET; y++) {
            for (int x = minPosition.getX() - OFFSET; x < maxPosition.getX() + OFFSET; x++) {
                Position pos = new Position(x,y);
                if (costs.get(pos) == null) {
                    costs.put(pos, 1);
                }
                allPositions.add(pos);
            }
        }
        allPositions.removeAll(nonTraverable);
    }

    private int getCost(Position pos) {
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(pos);
        List<SwampTile> swamp = EntityUtils.getEntitiesToTypeList(entities, SwampTile.class);
        return swamp.size() > 0 ? swamp.get(0).getMovementFactor() : 1;
    }

    // Gets neighbouring positions that are adjacent to the player
    private List<Position> getCardinalNeighbours(Position position) {
        List<Position> cardinalNeighbours = new ArrayList<>();
        for (Position adjacentPosition : position.getAdjacentFour()) {
            if (allPositions.contains(adjacentPosition)) {
                cardinalNeighbours.add(adjacentPosition);
            }
        }
        return cardinalNeighbours;
    }
    /**
     * Dijkstras algorithm
     * @param source
     */
    private void algorithm(Position source) {
        Position src = new Position(source.getX(), source.getY());

        dist = new HashMap<>();
        prev = new HashMap<>();
        // Set dist to infinity and position to null for all positions
        for (Position position : allPositions) {
            dist.put(position, Double.POSITIVE_INFINITY);
            prev.put(position, null);
        }
        // Set the src position to 0 dist
        dist.put(src, 0.0);
        // This will sort in ascending order of distance
        PriorityQueue<Position> queue = new PriorityQueue<>(allPositions.size(), new Comparator<Position>() {
            @Override
            public int compare(Position a, Position b) {
                return Double.compare(dist.get(a), dist.get(b));
            }
        });
        queue.addAll(allPositions);
        // Loop through everything
        while (!queue.isEmpty()) {
            Position next = queue.remove();
            for (Position neighbour : getCardinalNeighbours(next)) {
                if (dist.get(next) + costs.get(neighbour) < dist.get(neighbour)) {
                    dist.put(neighbour, dist.get(next) + costs.get(neighbour));
                    prev.put(neighbour, next);
                    queue.add(neighbour);
                }
            }

        }
    }

    public Position getNextPosition(Position destination) {
        Position dest = new Position(destination.getX(), destination.getY());
        Position src = new Position(entity.getPosition().getX(), entity.getPosition().getY());

        algorithm(src);
        Position current = dest;
        
        while (prev.get(current) != null && !prev.get(current).equals(src)) {
            current = prev.get(current);
        }

        return current;
    }

    public Position getPreviousPosition() {
        for (Position p : prev.keySet()) {
            if (prev.get(p) == null) {
                return p;
            }
        }
        return null;
    }
    public Map<Position, Position> getPreviousMap() {
        return this.prev;
    }

    public List<EntityResponse> drawPath(Position destination) {
        Position dest = new Position(destination.getX(), destination.getY());
        algorithm(entity.getPosition());

        Position current = dest;
        List<EntityResponse> responses = new ArrayList<>();
        while (current != null) {
            responses.add(new UnimplementedEntity(new Position(current.getX(), current.getY(), -1)).getEntityResponse());
            current = prev.get(current);
        }
        return responses;
    }
}
