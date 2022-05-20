package dungeonmania.entities.player.battle_strategies;

import dungeonmania.entities.Entity;

public interface BattleStrategy {
    /**
     * The battle strategy for this player
     * @param enemy
     */
    public void battle(Entity enemy);
}
