package dungeonmania.entities.moving_entities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.dungeon.util.InventorySaver;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.EntityDamage;
import dungeonmania.entities.behaviours.EntityHealth;
import dungeonmania.entities.behaviours.Teleportable;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.FollowMovement;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

//Mercenaries that have been bribed and will forever remain loyal to the player... as long as they are rich.
public class FriendlyMercenary extends MovingEntity implements EntityDamage, EntityHealth, Teleportable {

    private final static String TYPE = "mercenary_friendly";
    private final static int MAX_HEALTH = 5;
    private int friendly_period = Integer.MAX_VALUE;
    
    private Position prev_pos;

    public FriendlyMercenary(Position position) {
        super(TYPE, position);
        setHealth(MAX_HEALTH);
        setDamage(10);
        movement = new FollowMovement();
        defaultMovement = movement;
    }

    public FriendlyMercenary(String type, Position position) {
        super(type, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        setMoved(Direction.NONE);
        Position curPosition = getPosition();
        Player player = Dungeon.getInstance().getPlayer();
        prev_pos = getPosition();
        movement.move(this);
        if (EntityUtils.getEntitiesAtPosition(getPosition()).contains(player)) {
            setPosition(curPosition);
        }
        System.out.println(getHealth());
        friendly_period_tick();

    }
    @Override 
    public void onDeath() {
        for (Collectable item : getInventory()) {
            ((Entity)item).setPosition(new Position(getPosition().getX(), getPosition().getY()));
            Dungeon.getInstance().addEntity((Entity)item);
        }
        Dungeon.getInstance().getPlayer().removeAlly(this);
        Dungeon.getInstance().getEntities().remove(this);
    }
    /**
     * checks if the player is within the battle radius
     * @param p
     * @param entity
     * @return true if player in battle radius, false if not
     */
    public boolean checkBattleRadius(Player p) {
        List<Position> battleRadius = getPosition().getBattleRadius();
        List<Entity> eList = Dungeon.getInstance().getEntities();
        
        for (int i = 0; i < battleRadius.size(); i++) {
            Position pos = battleRadius.get(i);
            List<Entity> entities = eList.stream().filter(e -> (e.getPosition().equals(pos))).collect(Collectors.toList());
            if (entities.contains(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates when to convert a temporary friendly mercenary back into a mercenary if they have been bribed by the sceptre
     */
    public void friendly_period_tick() {
        
        friendly_period--;

        if (friendly_period <= 0) {

            Dungeon dungeon = Dungeon.getInstance();
            Player player = dungeon.getPlayer();

            dungeon.removeEntity(this);
            player.removeAlly(this);
            Mercenary enemy = new Mercenary(new Position(getPosition().getX(), getPosition().getY(), 2));
            dungeon.addEntity(enemy); 

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return true;
    }
    /**
     * Animations for the MovingEntities
     */
    @Override
    public List<AnimationQueue> getAnimationResponse(){
        animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("healthbar set " + (float)getHealth() / MAX_HEALTH, "healthbar tint 0x00ff00"), false, -1));
        return super.getAnimationResponse();
    }
    
    @Override
    public JsonObject toJsonObject(){
        JsonObject obj = super.toJsonObject();
        obj.addProperty("health", getHealth());
        obj.add("inventory", InventorySaver.inventoryToJsonArray(getInventory()));
        return obj;
    }


    /**
     * @return int return the friendly_period
     */
    public int getFriendly_period() {
        return friendly_period;
    }

    /**
     * @param friendly_period the friendly_period to set
     */
    public void setFriendly_period(int friendly_period) {
        this.friendly_period = friendly_period;
    }

    @Override
    public Position getPrevPosition() {
        return prev_pos;
    }

    @Override
    public Position getCurrPosition() {
        return getPosition();
    }
}
