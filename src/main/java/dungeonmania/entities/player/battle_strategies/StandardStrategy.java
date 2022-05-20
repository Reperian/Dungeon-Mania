package dungeonmania.entities.player.battle_strategies;

import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.util.BattleUtils;

public class StandardStrategy implements BattleStrategy {
    /**
     * Standard battle strategy for the player
     */
    @Override
    public void battle(Entity enemy) {
        BattleUtils.doPlayerBattle((Battleable)enemy);
    }
}
