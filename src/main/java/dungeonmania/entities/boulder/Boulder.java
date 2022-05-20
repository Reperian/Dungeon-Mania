package dungeonmania.entities.boulder;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.door.UnlockedDoor;
import dungeonmania.entities.floor_switch.FloorSwitch;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends Entity {
    private static final String TYPE = "boulder";
    
    /**
     * Constructor for a boulder
     * @param position the dungeon position of the boulder
     */
    public Boulder(Position position) {
        super(TYPE, position);
    }

    /**
    * {@inheritDoc}
    */
    @Override
    public void onTick() {}

    /**
    * {@inheritDoc}
    */
    @Override
    public boolean onOverlap(Entity _entity, Direction dir) {
        return push(dir);
    } 

    /** 
    * Push the boulder in the given direction
    * @param dir the direction to move the boulder
    * @return boolean true if the boulder was moved, otherwise false
    */
    private boolean push(Direction dir){
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(getPosition().translateBy(dir));

        List<FloorSwitch> switches = EntityUtils.getEntitiesToTypeList(entities, FloorSwitch.class);
        List<UnlockedDoor> unlockedDoors = EntityUtils.getEntitiesToTypeList(entities, UnlockedDoor.class);

        // if the only entity in the entities at that position are floor switches, move boulder to new position
        if (switches.size() + unlockedDoors.size() == entities.size()) {
            setPosition(getPosition().translateBy(dir));
            return true;
        }  

        return false;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public void init() {}
}
