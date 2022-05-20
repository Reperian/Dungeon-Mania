package dungeonmania.entities.door;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.key.Key;
import dungeonmania.entities.sunstone.SunStone;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LockedDoor extends Entity {
    private static final String TYPE = "door_locked";
    private int keyId;

    /** 
    * Constructor for a locked door
    * @param position the dungeon position of the exit
    * @param keyId the id of the key that unlocks the door
    */
    public LockedDoor(Position position, int keyId) {
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
        return unlock();         
    }

    /**
     * Use key to set the door to unlocked
     * @return true if the key Id matches the door Id, otherwise false 
     */
    public boolean unlockWithKey() {
        List<Collectable> keys = EntityUtils.getCollectablesFromInventory(Dungeon.getInstance().getInventory(), Key.class);
        if (keys.size() != 0) {
            Key key = (Key) keys.get(0);
            // If key is found remove from player inventory and set the door to unlocked
            if (key.getKeyId() == getKeyId()) {
                Dungeon.getInstance().getPlayer().removeItemFromInventory(key);
                return true;
            }
        }
        return false;    
    }

    /**
     * Use sun stone to set the door to unlocked
     * @return true if there is sun stone in player inventory, otherwise false
     */
    public boolean unlockWithSunStone() {
        
        List<Collectable> sunStones = EntityUtils.getCollectablesFromInventory(Dungeon.getInstance().getInventory(), SunStone.class);
        if (sunStones.size() != 0) {
            return true;
        }
        return false; 
    }

    /**
    * Unlocks the door if the right key is in the player's inventory
    * If the player has sun stone, it can unlock any doors
    * @return bool true if the door was unlocked, false otherwise
    */
    private boolean unlock() {
        if (unlockWithKey() || unlockWithSunStone()) {
            Dungeon.getInstance().addEntity(new UnlockedDoor(new Position(getPosition().getX(), getPosition().getY(), 0), keyId));
            Dungeon.getInstance().removeEntity(this);
            return true;
        }
        return false;
    }
    
    /**
    * Gets the keyId
    * @return int the id of the key that unlocks the door
    */
    public int getKeyId() {
        return keyId;
    }
    
    /**
    * {@inheritDoc}
    */
    @Override
    public void init() {}
}
