package dungeonmania.entities.moving_entities.util;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.behaviours.Defence;
import dungeonmania.entities.behaviours.EntityHealth;
import dungeonmania.entities.behaviours.Inventory;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.moving_entities.FriendlyMercenary;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.response.models.StatResponse;


public class BattleUtils {
    /**
     * Checks if there is a player battle that should be occuring
     * @param enemy
     */
    public static void checkBattlePlayer(Entity enemy) {
        List<Entity> entities = EntityUtils.getEntitiesAtPosition(enemy.getPosition());
        for (Entity e : entities) {
            if (e instanceof Player) {
                ((Player) e).battle(enemy);
            }
        }
    }

    private static void doBattle(Entity ally, MovingEntity enemy) {
        // calculate stats
        StatResponse allyStats = new StatResponse(ally, enemy);
        StatResponse enemyStats = new StatResponse(enemy, ally);
        // Do damage calculation
        float newAllyHp = (float)allyStats.getHealth() - (((enemyStats.getHealth() * enemyStats.getDamage()) / 10f) *
                                                            allyStats.getPercentageArmour() -
                                                            allyStats.getFlatArmour());

        float newEnemyHp = (float)enemyStats.getHealth() - (((allyStats.getHealth() * allyStats.getDamage()) / 5f) *
                                                            enemyStats.getPercentageArmour() - 
                                                            enemyStats.getFlatArmour()) * enemy.damageMultiplier();


        // Reduce durablility 
        List<Weapon> allyWeapons = EntityUtils.getInventoryToTypeList(((Inventory)ally).getInventory(), Weapon.class);
        List<Defence> allyArmours = EntityUtils.getInventoryToTypeList(((Inventory)ally).getInventory(), Defence.class);
        List<Weapon> enemyWeapons = EntityUtils.getInventoryToTypeList(((Inventory)enemy).getInventory(), Weapon.class);
        List<Defence> enemyArmours = EntityUtils.getInventoryToTypeList(((Inventory)enemy).getInventory(), Defence.class);
        allyWeapons.stream().forEach(e->e.reduceDurability(1));
        enemyWeapons.stream().forEach(e->e.reduceDurability(1));
        allyArmours.stream().forEach(e->e.reduceDurability(enemyStats.getTotalHits()));
        enemyArmours.stream().forEach(e->e.reduceDurability(allyStats.getTotalHits()));
        // Update new hp
        ((EntityHealth)ally).setHealth((int)newAllyHp);
        ((EntityHealth)enemy).setHealth((int)newEnemyHp);

    }

    /**
     * Simulates the battle for all friendly mercenaries
     * @param allies
     * @param enemy
     * @return
     */
    private static boolean doAllyBattle(List<FriendlyMercenary> allies, Battleable enemy) {
        for (FriendlyMercenary ally : allies) {
            doBattle(ally, (MovingEntity)enemy);
            if (enemy.getHealth() <= 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Simulates the process of player battling enemy
     * @param enemy
     * @return
     */
    public static void doPlayerBattle(Battleable enemy) {
        Player player = Dungeon.getInstance().getPlayer();
        // Loop till somebody dies
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            // Leaves battle if ally kills enemy
            if (doAllyBattle(player.getAllyList(), enemy)) {
                return;
            }
            doBattle(player, (MovingEntity)enemy);
        }
    }
}
