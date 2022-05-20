package dungeonmania.response.models;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Defence;
import dungeonmania.entities.behaviours.EntityDamage;
import dungeonmania.entities.behaviours.EntityHealth;
import dungeonmania.entities.behaviours.Inventory;
import dungeonmania.entities.behaviours.Weapon;
import dungeonmania.entities.util.EntityUtils;

public final class StatResponse {
    private float percentageArmour;
    private float flatArmour;
    private float damage;
    private int totalHits;
    private float health;

    public StatResponse(Entity entity, Entity enemy) {
        damage = ((EntityDamage)entity).getDamage();
        health = ((EntityHealth)entity).getHealth();
        List<Collectable> inventory = ((Inventory)entity).getInventory();
        calculateArmour(inventory);
        calculateAttack(inventory, enemy);
    }

    private void calculateArmour(List<Collectable> inventory) {
        List<Defence> defensiveEquipment = EntityUtils.getInventoryToTypeList(inventory, Defence.class);
        float totalPercentageArmour = 1f;
        float totalFlatArmour = 0;
        for (Defence equipment : defensiveEquipment) {
            totalPercentageArmour *= equipment.getPercentageDefence();
            totalFlatArmour += equipment.getFlatDefence();
        }
        flatArmour = totalFlatArmour;
        percentageArmour = totalPercentageArmour;
    }
    private void calculateAttack(List<Collectable> inventory, Entity enemy) {
        List<Weapon> weapons = EntityUtils.getInventoryToTypeList(inventory, Weapon.class);
        int totalHits = 1;
        float totalDamage = damage;
        for (Weapon equipment : weapons) {
            totalHits += equipment.getHitMultiplier();
            totalDamage += equipment.getDamage() * equipment.getEntityTypeMultiplier(enemy);
        }
        damage = (float) totalDamage * totalHits / (weapons.size() + 1);
        this.totalHits = totalHits;
    }

    /**
     * @return float return the percentageArmour
     */
    public float getPercentageArmour() {
        return percentageArmour;
    }

    /**
     * @return float return the flatArmour
     */
    public float getFlatArmour() {
        return flatArmour;
    }
    /**
     * @return float return the damage
     */
    public float getDamage() {
        return damage;
    }

    /**
     * @return int return the totalHits
     */
    public int getTotalHits() {
        return totalHits;
    }

    /**
     * @return float return the health
     */
    public float getHealth() {
        return health;
    }
}
