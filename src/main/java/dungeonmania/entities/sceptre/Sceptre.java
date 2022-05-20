package dungeonmania.entities.sceptre;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.arrows.Arrows;
import dungeonmania.entities.behaviours.Buildable;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.key.Key;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.sunstone.SunStone;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.entities.wood.Wood;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Sceptre extends Entity implements Collectable, Buildable {
    
    private static final String TYPE = "sceptre";

    /**
     * Constructs a sceptre if a sceptre can be built from items in the player's inventory
     */
    public Sceptre() {
        super(TYPE, new Position(0,0));
        build();
    }

    /**
     * Constructs a sceptre, no need to check the player's inventory as this is only used when the sceptre is loaded from a json map
     * @param position position the sceptre is spawned at on the map
     */
    public Sceptre(Position position) {
        super(TYPE, position);
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
     * Builds the sceptre if the player has sufficient items to build it by removing recipe items from the player's inventory
     * @throws InvalidActionException if there aren't enough Collectables to build the bos
     */
    public void build() throws InvalidActionException {

        Player p = Dungeon.getInstance().getPlayer();

        if (isBuildable()) {

            List<Collectable> inventory = p.getInventory();

            int numWood = EntityUtils.getEntitiesFromInventory(inventory, Wood.class).size();
            int numKey = EntityUtils.getEntitiesFromInventory(inventory, Key.class).size();

            if (numWood >= 1) {

                p.removeNumOfTypeFromInventory(1, Wood.class);

            } else {

                p.removeNumOfTypeFromInventory(2, Arrows.class);
                
            }

            if (numKey >= 1) {

                p.removeNumOfTypeFromInventory(1, Key.class);

            } else {

                p.removeNumOfTypeFromInventory(1, Treasure.class);

            }

            p.removeNumOfTypeFromInventory(1, SunStone.class);

        } else {
            throw new InvalidActionException("Insufficient Items to build Sceptre");
        }

    }
    
    /**
     * Checks if a sceptre is buildable from the items in the player's inventory
     * @return whether or not a sceptre is buildable
     */
    public static boolean isBuildable() {

        Dungeon dungeon = Dungeon.getInstance();
        Player player = dungeon.getPlayer();
        List<Collectable> inventory = player.getInventory();

        int numWood = EntityUtils.getEntitiesFromInventory(inventory, Wood.class).size();
        int numArrows = EntityUtils.getEntitiesFromInventory(inventory, Arrows.class).size();
        int numKey = EntityUtils.getEntitiesFromInventory(inventory, Key.class).size();
        int numTreasure = EntityUtils.getEntitiesFromInventory(inventory, Treasure.class).size();
        int numSunStone = EntityUtils.getEntitiesFromInventory(inventory, SunStone.class).size();

        return (numWood >= 1 || numArrows >= 2) && (numKey >= 1 || numTreasure >= 1) && numSunStone >= 1;

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
