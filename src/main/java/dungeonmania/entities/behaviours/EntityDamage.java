package dungeonmania.entities.behaviours;

public interface EntityDamage {
    /**
     * 
     * @return a map of the character with the damage it will get
     */
    public int getDamage();

    /**
     * damage multiplier based on entity's armor and shields
     * the multiplier is used in battle to calculate damage dealt
     * @return damage multiplier
     */
    public float damageMultiplier();
}
