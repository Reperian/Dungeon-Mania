package dungeonmania.dungeon.map_generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.JsonArray;

import dungeonmania.entities.Entity;
import dungeonmania.entities.exit.Exit;
import dungeonmania.entities.wall.Wall;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class PrimsAlgorithm {
    private static final boolean WALL = false;
    private static final boolean EMPTY = true;
    private static final Random rand = new Random();
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private boolean[][] map = new boolean[HEIGHT][WIDTH];
    private final int xStart, yStart, xEnd, yEnd;
    /**
     * Constructor for the prims algorithm
     * @precondition all inputs are within [0,50)
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     */
    public PrimsAlgorithm(int xStart, int yStart, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }
    /**
     * Returns the value at position
     * @preconditions X and Y for pos must be in [0,50)
     * @param pos
     * @return boolean
     */
    private boolean valueAtPosition(Position pos) {
        return map[pos.getY()][pos.getX()];
    }
    /**
     * Returns the middle position of two positions
     * @param a
     * @param b
     * @return Position
     */
    private Position getCenterPosition(Position a, Position b) {
        return new Position((a.getX() + b.getX()) / 2, (a.getY() + b.getY()) / 2);
    }
    /**
     * Checks if the position is in boundary of the walls
     * @param pos
     * @return boolean
     */
    private boolean isInBoundary(Position pos) {
        return !((0 > pos.getX() || pos.getX() >= WIDTH - 1) || (0 > pos.getY() || pos.getY() >= HEIGHT - 1));
    }
    /**
     * Returns a list of neighbors of distance away from a position with a certain value
     * @param pos
     * @param distance
     * @param value
     * @return List<Position>
     */
    private List<Position> getNeighboursWithValue(Position pos, int distance, boolean value) {
        List<Position> neighbours = new ArrayList<>();
        for (Direction offset : Direction.values()) {
            // By translating a scaled version of the offest we can find neighbouring squares
            Position translatedPosition = pos.translateBy(offset.getOffset().scale(distance));
            if (isInBoundary(translatedPosition) && valueAtPosition(translatedPosition) == value && !translatedPosition.equals(pos)) {
                neighbours.add(translatedPosition);
            }
        }
        return neighbours;
    }
    // Set the value at a position to 'value'
    private void setMapValue(Position pos, boolean value) {
        map[pos.getY()][pos.getX()] = value;
    }

    public void runAlgorithm() {
        // Start with all walls
        map = new boolean[HEIGHT][WIDTH];
        Set<Position> options = new HashSet<>();
        //List<Position> options = new ArrayList<>();
        // set start to empty
        map[yStart][xStart] = true;
        // Add starting position neigbouring walls to options
        Position next = new Position(xStart, yStart);
        options.addAll(getNeighboursWithValue(next, 2, WALL));
        // For all remaining positions
        while (!options.isEmpty()) {
            // Choose next
            next = options.toArray(new Position[]{})[rand.nextInt(options.size())];
            options.remove(next);
            // Pick random neighbour dist 2 away and connect neighbour and next with empty space
            List<Position> neighbours = getNeighboursWithValue(next, 2, EMPTY);
            if (!neighbours.isEmpty()) {
                Position neighbour = neighbours.get(rand.nextInt(neighbours.size()));
                setMapValue(next, EMPTY);
                setMapValue(getCenterPosition(next, neighbour), EMPTY);
                setMapValue(neighbour, EMPTY);
            }
            // Add cardinal walls of dist 2 of 'next'
            options.addAll(getNeighboursWithValue(next, 2, WALL));

        }
        // Checks if the exit is on a wall
        if (map[yEnd][xEnd] != EMPTY) {
            map[yEnd][xEnd] = EMPTY;
            List<Position> endNeighbours = getNeighboursWithValue(new Position(xEnd, yEnd), 1, WALL);
            if (endNeighbours.size() > 0) {
                Position endNeighbour = endNeighbours.get(rand.nextInt(endNeighbours.size()));
                map[endNeighbour.getY()][endNeighbour.getX()] = EMPTY;
            }
        }
    }
    /**
     * Converts the maze into a list of entities
     * @return List<Entity>
     */
    private List<Entity> convertToEntities() {
        List<Entity> entities = new ArrayList<>();
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Position pos = new Position(col, row);
                if (valueAtPosition(pos) == WALL) {
                    entities.add(new Wall(pos));
                }
            }
        }
        entities.add(new Exit(new Position(xEnd, yEnd)));
        return entities;
    }
    // converts the map into a json array for loading
    public JsonArray mapToJsonArray() {
        JsonArray entities = new JsonArray();
        convertToEntities().stream().forEach(e -> entities.add(e.toJsonObject()));
        return entities;
    }
}

