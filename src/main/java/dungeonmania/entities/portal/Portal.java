package dungeonmania.entities.portal;

import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Teleportable;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends Entity {
    
    private static final String TYPE = "portal";
    Portal correspondingPortal = null;

    private String colour;

    /**
     * Constructor for the portal
     * @param position
     * @param colour
     */
    public Portal(Position position, String colour) {
        super(TYPE + "_" + colour, position);
        this.colour = colour;
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
        obj.addProperty("colour", colour);
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(getPosition());
        List<Teleportable> teleportable_entities = EntityUtils.getEntitiesToTypeList(entities, Teleportable.class);

        for (Teleportable t : teleportable_entities) {
            boolean success = teleport((MovingEntity)t, t.getDirectionEntered());

            if (!success) {
                Position prev_pos = t.getPrevPosition();
                ((MovingEntity)t).setPosition(prev_pos);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return teleport(dir);
    }

    /**
     * Teleports the player to the position dir away from the corresponding portal
     * @param dir
     * @return false - the player shouldn't move as it has already been teleported
     */
    private boolean teleport(Direction dir) {
        if (correspondingPortal == null){
            throw new InvalidActionException("Portal does not have a corresponding portal");
        }

        Player player = Dungeon.getInstance().getPlayer();

        if (player.checkMove(correspondingPortal.getPosition().translateBy(dir), dir)) {
            player.setPosition(correspondingPortal.getPosition().translateBy(dir));
        } 
        return false;
    }

    /**
     * Teleports the player to the position dir away from the corresponding portal
     * @param entity the entity to be teleported
     * @param dir the direction to be teleported to in relation to the corresponding portal
     */
    private boolean teleport(MovingEntity entity, Direction dir) {
        if (correspondingPortal == null){
            throw new InvalidActionException("Portal does not have a corresponding portal");
        }

        if (entity.getMovementStrategy().checkMove(correspondingPortal.getPosition().translateBy(dir))) {
            entity.setPosition(correspondingPortal.getPosition().translateBy(dir));
            return true;
        } 
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public String getColour(){
        return colour;
    }

    /**
     * {@inheritDoc}
     */
    public Portal getCorresponding(){
        return correspondingPortal;
    }

    /**
     * {@inheritDoc}
     */
    public void link(Portal portal){
        if (portal.getColour() == this.getColour()) {
            this.correspondingPortal = portal;
        }  
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        List<Portal> portals = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Portal.class);

        List<Portal> matchingPortals = portals.stream()
                                              .filter(p -> (p.getColour().equals(this.getColour())) && (p != this))
                                              .collect(Collectors.toList());

        if (matchingPortals.size() < 1) {
            throw new InvalidActionException("Dungeon is invalid: Missing corresponding portal - a portal must be linked to exactly one other portal");
        } else if (matchingPortals.size() > 1) {
            throw new InvalidActionException("Dungeon is invalid: Too many corresponding portals - a portal must be linked to exactly one other portal");
        }

        correspondingPortal = matchingPortals.get(0);

        // If a corresponding portal exists and it's correspondingPortal attribute has not yet been set, set it to this
        if (correspondingPortal.getCorresponding() != null) {
            correspondingPortal.link(this);
        }
    }
}
