package dungeonmania.entities.behaviours;

public interface EntityHealth {
    /**
     * 
     * @return the character's health
     */
    public int getHealth();

    /**
     * Change the character's health
     * @param health 
     */
    public void setHealth(int health);
}
