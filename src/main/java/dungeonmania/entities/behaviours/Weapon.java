package dungeonmania.entities.behaviours;

import dungeonmania.entities.Entity;

public interface Weapon extends Gear {
    /**
     * returns the base damage 
     * @return int
     */
    public float getDamage();
    /**
     * returns the amount of hits this weapon will do
     * @return int
     */
    public int getHitMultiplier();
    /**
     * Returns the multiplier based on the enemy type
     * @param entity
     * @return int
     */
    default public int getEntityTypeMultiplier(Entity entity) {
        return 1;
    };
}
