package dungeonmania.entities.midnight_armour;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.armour.Armour;
import dungeonmania.entities.behaviours.Buildable;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Defence;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.moving_entities.ZombieToast;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.sunstone.SunStone;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MidnightArmour extends Entity implements Collectable, Buildable, Defence, Weapon {
    
    private static final String TYPE = "midnight_armour";
    public static final float DAMAGE = 2;
    public static final int HIT_MULTIPLIER = 1;
    private static final float DAMAGE_REDUCTION = 0.5f;
    private int durability;

    /**
     * Constructs a midnight armour if a midnight armour can be built from items in the player's inventory
     */
    public MidnightArmour() {
        super(TYPE, new Position(0,0));
        build();
        this.durability = 5;
    }

    /**
     * Constructs a midnight armour, no need to check the player's inventory as this is only used when the midnight armour is loaded from a json map
     * @param position position the midnight armour is spawned at on the map
     */
    public MidnightArmour(Position position) {
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
     * Builds the midnight armour if the player has sufficient items to build it by removing recipe items from the player's inventory
     * @throws InvalidActionException if there aren't enough Collectables to build the bos
     */
    public void build() throws InvalidActionException {

        Player p = Dungeon.getInstance().getPlayer();

        if (isBuildable()) {

            p.removeNumOfTypeFromInventory(1, Armour.class);
            p.removeNumOfTypeFromInventory(1, SunStone.class);

        } else {
            throw new InvalidActionException("Insufficient Items to build Midnight Armour");
        }

    }
    
    /**
     * Checks if a midnight armour is buildable from the items in the player's inventory
     * @return whether or not a midnight armour is buildable
     */
    public static boolean isBuildable() {

        Dungeon dungeon = Dungeon.getInstance();
        List<Entity> dungeon_entities = dungeon.getEntities();
        Player player = dungeon.getPlayer();

        int numArmour = EntityUtils.getEntitiesFromInventory(player.getInventory(), Armour.class).size();
        int numZombieToast = EntityUtils.getEntitiesOfTypeList(dungeon_entities, ZombieToast.class).size();
        int numSunStone = EntityUtils.getEntitiesFromInventory(player.getInventory(), SunStone.class).size(); 

        return (numArmour >= 1 && numSunStone >= 1 && numZombieToast == 0);

    }

    /**
     * @return int returns the durability
     */
    public int getDurability() {
        return durability;
    }
    /**
     * Reduces durability of midnight armour by amount
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

    /**
     * {@inheritDoc}
     */
    @Override
    public float getFlatDefence() {

        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPercentageDefence() {
        return DAMAGE_REDUCTION;
    }

}
