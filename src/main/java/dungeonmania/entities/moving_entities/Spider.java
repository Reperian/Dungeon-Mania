package dungeonmania.entities.moving_entities;

import java.util.List;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Battleable;
import dungeonmania.entities.moving_entities.util.BattleUtils;

import dungeonmania.movement.MovementStrategy;
import dungeonmania.movement.InitialMovement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends MovingEntity implements Battleable {
    private final static String TYPE = "spider";
    
    private List<Position> adjacent = getPosition().getAdjacentPositions();

    public Spider(Position position) {
        super(TYPE, position);
        setHealth(1);
        setDamage(10);
        movement = new InitialMovement();
        defaultMovement = movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onTick() {
        setMoved(Direction.NONE);
        movement.move(this);
        BattleUtils.checkBattlePlayer(this);
    }
    
    public List<Position> getAdjacent() {
        return adjacent;
    }

    public void setStrategy(MovementStrategy strategy) {
        this.movement = strategy;
        defaultMovement = movement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        Dungeon.getInstance().getPlayer().battle(this);
        return true;         
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
    }

}
