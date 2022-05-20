package dungeonmania.entities.zombie_toast_spawner;

import dungeonmania.util.Position;

public class StandardZombieToastSpawner extends ZombieToastSpawner {

    private static final int TICKS_TIL_SPAWN = 19;
    int tickCounter = 0;

    /**
     * Constructor for the standard ZombieToastSpawner
     * @param position     
     */
    public StandardZombieToastSpawner(Position position) {
        super(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        
        if (tickCounter < TICKS_TIL_SPAWN) {
            tickCounter++;
            return;
        } 

        spawnZombieToast();
        tickCounter = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {}
}
