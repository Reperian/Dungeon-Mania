package dungeonmania.entities.zombie_toast_spawner;

import dungeonmania.util.Position;

public class ZombieToastSpawnerFactory {

    /**
     * Constructs a ZombieZToastSpawner depending on gameMode
     * @param pos
     * @param gameMode
     */
	public static ZombieToastSpawner getSpawner(Position pos, String gameMode) {
		ZombieToastSpawner spawner = null;

        switch(gameMode) {
            case("Standard"):
                spawner = new StandardZombieToastSpawner(pos);
                break;
            case("Peaceful"):
                spawner = new StandardZombieToastSpawner(pos);
                break;
            case("Hard"):
                spawner = new HardZombieToastSpawner(pos);    
                break;
            default:
                new IllegalArgumentException("Invalid Game Mode");
        }

		return spawner;
	}
}
