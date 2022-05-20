package dungeonmania.entities.key;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Key extends Entity implements Collectable {
    private static final String TYPE = "key";

    private int keyId;

    /**
     * Constructor for the key
     * @param position
     * @param keyId
     */
    public Key(Position position, int keyId) {
        super(TYPE + "_" + String.valueOf(keyId), position);
        this.keyId = keyId;
    }

    /**
     * Call the key's ID
     * @return key ID
     */
    public int getKeyId() {
        return keyId;
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
        return collect();         
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean collect() {
        // Call the player
        Player player = Dungeon.getInstance().getPlayer();
        // Call the list of keys that are collected into the player's inventory
        List<Collectable> keysCollected = EntityUtils.getCollectablesFromInventory(player.getInventory(), Key.class);
        if (keysCollected.size() > 0) { 
            return true;
        }
        player.addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemResponse getItemResponse() {
        return new ItemResponse(getId(), getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
