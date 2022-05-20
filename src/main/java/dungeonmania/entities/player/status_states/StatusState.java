package dungeonmania.entities.player.status_states;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.player.Player;

public abstract class StatusState {
    protected static final int TOTAL_TICKS = 7;

    int remainingTicks;
    
    protected StatusState() {
        this.remainingTicks = TOTAL_TICKS;
        Dungeon.getInstance().getPlayer().setStatus(this);
    }
    /**
     * The actions performed when transitioning to next state
     */
    public abstract void onTransition();
    /**
     * Move onto next state when called
     */
    public void nextState() {
        if (remainingTicks <= 0) {
            Player player = Dungeon.getInstance().getPlayer();
            player.setBattleStrategy(Player.getDefaultBattleStrategy());
            player.setStatus(Player.getDefaultStatus());
        }
        remainingTicks--;
        onTransition();
    }
}
