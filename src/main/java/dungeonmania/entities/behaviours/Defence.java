package dungeonmania.entities.behaviours;

public interface Defence extends Gear {
    /**
     * Returns the flat defence of this item
     * @return
     */
    public float getFlatDefence();
    /**
     * Returns the percentage defence of this item
     * @return
     */
    public float getPercentageDefence();
}
