package dungeonmania.entities.shield;

import java.util.List;

import com.google.gson.JsonObject;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Buildable;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Defence;
import dungeonmania.entities.key.Key;
import dungeonmania.entities.moving_entities.util.InventoryUtils;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wood.Wood;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Shield extends Entity implements Collectable, Buildable, Defence {
    private static final String TYPE = "shield";
    private static float FLAT_ARMOUR = 0.5f;
    
    private int durability;

    /**
     * Constructs a shield if a shield can be built from items in the player's inventory
     */
    public Shield() {
        super(TYPE, new Position(0,0));
        build();
        this.durability = 5;
    }

    /**
     * Constructs a shield, no need to check the player's inventory as this is only used when the shield is loaded from a json map
     * @param position position the shield is spawned at on the map
     */
    public Shield(Position position) {
        super(TYPE, position);
        this.durability = 5;
    }

    /**
     * Calculates the total damage reduction 
     * @param inventory 
     * @return float the total flat armour in the inventory
     */
    public static float totalFlatArmour(List<Collectable> inventory){
        return EntityUtils.getInventoryToTypeList(inventory, Shield.class).size() * FLAT_ARMOUR;
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
     * Builds the shield if the player has sufficient items to build it by removing recipe items from the player's inventory
     */
    public void build() throws InvalidActionException {

        Player p = Dungeon.getInstance().getPlayer();

        if (isBuildable()) {

            int numTreasure = EntityUtils.getEntitiesFromInventory(p.getInventory(), Treasure.class).size();
            
            // prioritises using a treasure in the recipe for a shield over a key

            if (numTreasure >= 1) {
                p.removeNumOfTypeFromInventory(2, Wood.class);
                p.removeNumOfTypeFromInventory(1, Treasure.class);
            } else {
                p.removeNumOfTypeFromInventory(2, Wood.class);
                p.removeNumOfTypeFromInventory(1, Key.class);
            }

        } else {
            throw new InvalidActionException("Insufficient Items to build Shield");
        }
    }

    /**
     * Checks if a shield is buildable from the items in the player's inventory
     * @return whether or not a shield is buildable
     */
    public static boolean isBuildable() {

        Player player = Dungeon.getInstance().getPlayer();

        int numWood = EntityUtils.getEntitiesFromInventory(player.getInventory(), Wood.class).size();
        int numTreasure = EntityUtils.getEntitiesFromInventory(player.getInventory(), Treasure.class).size();
        int numKey= EntityUtils.getEntitiesFromInventory(player.getInventory(), Key.class).size();

        return (numWood >= 2 && numTreasure >= 1) || (numWood >= 2 && numKey >= 1);
    }

    /**
     * @return int return the durability
     */
    public int getDurability() {
        return durability;
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
     * {@inheritDoc}
     */
    @Override
    public void reduceDurability(int amount) {
        durability -= amount;
        if (durability <= 0) {
            InventoryUtils.removeItemIdFromInventory(Dungeon.getInstance().getPlayer().getInventory(), getId());
        }
        
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = super.toJsonObject();
        obj.addProperty("durability", durability);
        return obj;
    }
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
    public float getFlatDefence() {
        return FLAT_ARMOUR;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public float getPercentageDefence() {
        return 0;
    }
}
