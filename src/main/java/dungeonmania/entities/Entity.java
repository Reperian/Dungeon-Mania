package dungeonmania.entities;

import java.util.*;

import com.google.gson.JsonObject;

import dungeonmania.entities.behaviours.Interactable;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class Entity {
    private Position position;
    private String id = UUID.randomUUID().toString();
    private String type;
    protected List<AnimationQueue> animationBuffer = new ArrayList<>();
    /**
     * Constructor for the entity class
     * @param type
     * @param position
     */
    protected Entity(String type, Position position) {
        this.position = position;
        this.type = type;
    }

    /**
     * Governs the action performed by the entity when the game updates it's tick
     */
    public abstract void onTick();
    
    /**
     * Actions enacted by this entity when a player overlaps with this entity
     * @param entity
     * @param dir
     * @return true if the entity that overlaps this can move into this position
     */
    public abstract boolean onOverlap(Entity entity, Direction dir);
    
    /**
     * Initialise the entity
     */
    public abstract void init();
    
    /**
     * Gets the entity Response of this entity
     * @return EntityResponse
     */
    public EntityResponse getEntityResponse() {
        return new EntityResponse(id, type, position, Interactable.class.isInstance(this));
    }
    
    public List<AnimationQueue> getAnimationResponse() {
        List<AnimationQueue> animationQueues = new ArrayList<>(animationBuffer);
        animationBuffer = new ArrayList<>();
        return animationQueues;
    }
    /**
     * @return Position return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }   

    /**
     * returns this entity's details in the form of an json object
     * @return JsonObject
     */
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", position.getX());
        obj.addProperty("y", position.getY());
        obj.addProperty("type", type);
        return obj;
    }
}
