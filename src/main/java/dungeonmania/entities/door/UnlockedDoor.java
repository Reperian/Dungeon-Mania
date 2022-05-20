package dungeonmania.entities.door;

import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class UnlockedDoor extends Entity {
    private static final String TYPE = "door_unlocked";
    private int keyId;

    /** 
    * Constructor for an unlocked door
    * @param position the dungeon position of the exit
    * @param keyId the id of the door that the key opens
    */
    public UnlockedDoor(Position position, int keyId) {
        super(TYPE + "_" + String.valueOf(keyId), position);
        this.keyId = keyId;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", getPosition().getX());
        obj.addProperty("y", getPosition().getY());
        obj.addProperty("type", TYPE);
        obj.addProperty("key", keyId);
        return obj;
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
    public boolean onOverlap(Entity entity, Direction dir) {
        return true;         
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
