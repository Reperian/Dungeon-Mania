package dungeonmania.entities.player.status_states;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.StuckMovement;

public class DefaultState extends StatusState{

    public DefaultState() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTransition() {
        Player player = Dungeon.getInstance().getPlayer();
        player.setBattleStrategy(Player.getDefaultBattleStrategy());
        // Sets all enemies movement strategy to default movements
        List<Battleable> enemies = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Battleable.class);
        for (Battleable enemy : enemies) {
            if (!(((MovingEntity)enemy).getMovementStrategy() instanceof StuckMovement)) {
                ((MovingEntity)enemy).setMovementStrategy(((MovingEntity)enemy).getDefaultMovementStrategy());
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextState(){
        onTransition();
    }
}
