package dungeonmania.entities.floor_switch;

import java.util.List;
import dungeonmania.entities.Entity;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class FloorSwitch extends Entity {
    private static final String TYPE = "switch";
    private boolean triggered = false;
    
    /**
     * Constructor for FloorSwitch
     * @param position the dungeon position of the switch
     */
    public FloorSwitch(Position position) {
        super(TYPE, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        // check if a boulder is on top of it
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(this.getPosition());
       
        List<Boulder> boulders = EntityUtils.getEntitiesToTypeList(entities, Boulder.class);
        
        // if there is an entity on a switch at that entity is a boulder, trigger switch
        if (boulders.size() == 1) {
            setTriggerOn();
        } else {
            setTriggerOff();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity _entity, Direction dir) {
        return true; 
    }

    /**
     * Checks whether the switch is triggered or not
     * @return boolean return if the switch is triggered
     */
    public boolean isTriggered() {
        return triggered;
    }

    /**
     * Toggles the trigger status to on
     */
    private void setTriggerOn() {
        triggered = true;
    }

    /**
     * Toggles the trigger status to off
     */
    private void setTriggerOff() {
        triggered = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
