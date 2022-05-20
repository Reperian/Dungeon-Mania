package dungeonmania.entities.zombie_toast_spawner;

import dungeonmania.util.Position;

public class HardZombieToastSpawner extends StandardZombieToastSpawner{

    private static final int TICKS_TIL_SPAWN = 14;
    int tickCounter = 0;
    
    /**
     * Constructor for hardMode ZombieToastSpawner
     * @param position
     */
    public HardZombieToastSpawner(Position position) {
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
}
