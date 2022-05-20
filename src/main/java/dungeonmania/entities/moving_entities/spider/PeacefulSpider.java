package dungeonmania.entities.moving_entities.spider;

import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.movement.InitialMovement;
import dungeonmania.util.Position;

public class PeacefulSpider extends Spider {
    /**
     * Constructor for the PeacefulSpider class
     * @param position
     */
    public PeacefulSpider(Position position) {
        super(position);
        setHealth(1);
        setDamage(0);
        movement = new InitialMovement();
    }
}
