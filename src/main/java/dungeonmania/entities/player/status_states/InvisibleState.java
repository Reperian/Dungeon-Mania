package dungeonmania.entities.player.status_states;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.player.battle_strategies.AvoidBattleStrategy;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.FollowMovement;
import dungeonmania.movement.RandomMovement;

public class InvisibleState extends StatusState {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTransition() {
        // Player will avoid battles 
        Dungeon.getInstance().getPlayer().setBattleStrategy(new AvoidBattleStrategy());
        List<Battleable> enemies = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Battleable.class);
        // All enemies that follow the player will use random movement
        for (Battleable enemy : enemies) {
            if (((MovingEntity)enemy).getMovementStrategy() instanceof FollowMovement) {
                ((MovingEntity)enemy).setMovementStrategy(new RandomMovement());
            }
        }
    }
}
