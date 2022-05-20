package dungeonmania.entities.player.battle_strategies;

import dungeonmania.entities.Entity;
import dungeonmania.entities.moving_entities.MovingEntity;

public class InstantKillStrategy implements BattleStrategy {
    /**
     * sets the hp of the enemy to 0 effectively killing them instantly
     * without taking damage
     */
    @Override
    public void battle(Entity enemy) {
        ((MovingEntity)enemy).setHealth(0);
    }
}
