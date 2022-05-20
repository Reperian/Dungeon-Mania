package dungeonmania.entities.bow;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.arrows.Arrows;
import dungeonmania.entities.behaviours.Buildable;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wood.Wood;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bow extends Entity implements Collectable, Buildable, Weapon { 
    private static final String TYPE = "bow";
    private static final int DAMAGE = 0;
    private static final int HIT_MULTIPLIER = 2;
    private int durability;

    /**
     * Constructs a bow if a bow can be built from items in the player's inventory
     */
    public Bow() {
        super(TYPE, new Position(0,0));
        build();
        this.durability = 5;
    }

    /**
     * Constructs a bow, no need to check the player's inventory as this is only used when the bow is loaded from a json map
     * @param position position the bow is spawned at on the map
     */
    public Bow(Position position) {
        super(TYPE, position);
        this.durability = 5;
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
        Dungeon.getInstance().getPlayer().addItemToInventory(this);
        Dungeon.getInstance().removeEntity(this);
        return true;
    }

    /**
     * Builds the bow if the player has sufficient items to build it by removing recipe items from the player's inventory
     * @throws InvalidActionException if there aren't enough Collectables to build the bos
     */
    public void build() throws InvalidActionException {

        if (isBuildable()) {
            Player p = Dungeon.getInstance().getPlayer();
            p.removeNumOfTypeFromInventory(1, Wood.class);
            p.removeNumOfTypeFromInventory(3, Arrows.class);
        } else {
            throw new InvalidActionException("Insufficient Items to build Bow");
        }
    }
    
    /**
     * Checks if a bow is buildable from the items in the player's inventory
     * @return whether or not a bow is buildable
     */
    public static boolean isBuildable() {

        Player player = Dungeon.getInstance().getPlayer();

        int numWood = EntityUtils.getEntitiesFromInventory(player.getInventory(), Wood.class).size();
        int numArrows = EntityUtils.getEntitiesFromInventory(player.getInventory(), Arrows.class).size();

        if (numWood >= 1 && numArrows >= 3) {
            return true;

        }

        return false;
    }

    /**
     * @return int returns the durability
     */
    public int getDurability() {
        return durability;
    }
    /**
     * Reduces durability of bow by amount
     * @param amount
     */
    @Override
    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability <= 0) {
            InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
        }
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

    /**
     * @param durability the durability to set
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getDamage() {
        return DAMAGE;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getHitMultiplier() {
        return HIT_MULTIPLIER;
    }

}
