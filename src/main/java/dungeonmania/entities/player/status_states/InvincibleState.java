package dungeonmania.entities.player.status_states;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.MovingEntity;
import dungeonmania.entities.player.battle_strategies.InstantKillStrategy;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.movement.RunAwayMovement;
import dungeonmania.movement.StuckMovement;

public class InvincibleState extends StatusState {

    public InvincibleState() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTransition() {
        // Potion no effect if hardmode
        if (Dungeon.getInstance().getGameMode().equals("Hard")) {
            return;
        }
        // sets the player to instantly kill all enemies and the sets all enemies to flee player
        Dungeon.getInstance().getPlayer().setBattleStrategy(new InstantKillStrategy());
        List<Battleable> enemies = EntityUtils.getEntitiesToTypeList(Dungeon.getInstance().getEntities(), Battleable.class);
        for (Battleable enemy : enemies) {
            if (!(((MovingEntity)enemy).getMovementStrategy() instanceof StuckMovement)) {
                ((MovingEntity)enemy).setMovementStrategy(new RunAwayMovement());
            }
        }
    }
}
