package dungeonmania.entities.swamp_tile;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.StuckMovement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTile extends Entity{

    private int movement_factor;
    private static final int TICKS_TO_TRAVERSE = 1;
    private static final String TYPE = "swamp_tile";
    List<MovingEntity> stuck_entities = new ArrayList<MovingEntity>();

    /**
     * Constructor for a SwampTile
     */
    public SwampTile(Position position, int movement_factor) {
        super(TYPE, position);
        this.movement_factor = movement_factor;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", getPosition().getX());
        obj.addProperty("y", getPosition().getY());
        obj.addProperty("movement_factor", movement_factor);
        return obj;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void onTick() {
        remove_old_entities();
        get_new_entities();
    }

     /**
     * removes old entities from the stuck entities lise
     */
    private void remove_old_entities() { 
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(getPosition());
        
        List<MovingEntity> moving_entities = EntityUtils.getEntitiesToTypeList(entities, MovingEntity.class);

        for (int i = 0; i < stuck_entities.size(); i++) {
            if (!moving_entities.contains(stuck_entities.get(i))) {
                stuck_entities.remove(stuck_entities.get(i));
            }
        }
    }

    /**
     * Gets any new entities that have moved onto the swamp tile and adds them to the stuck_entities list
     */
    private void get_new_entities() { 
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(getPosition());
        
        List<MovingEntity> moving_entities = EntityUtils.getEntitiesToTypeList(entities, MovingEntity.class);

        for (MovingEntity m : moving_entities) {
            if (!stuck_entities.contains(m)) {
                m.setMovementStrategy(new StuckMovement(TICKS_TO_TRAVERSE * movement_factor - 1));
                stuck_entities.add(m);
            }
        }
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return true;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void init() {
        get_new_entities();
    }
    /**
     * Returns the movement factor
     * @return int
     */
    public int getMovementFactor() {
        return movement_factor;
    }
}
