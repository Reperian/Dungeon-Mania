package dungeonmania.entities.zombie_toast_spawner;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.armour.Armour;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.behaviours.Interactable;
import dungeonmania.entities.bow.Bow;
import dungeonmania.entities.sword.Sword;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.entities.moving_entities.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class ZombieToastSpawner extends Entity implements Interactable {
    private static final String TYPE = "zombie_toast_spawner";
    private static final float ARMOUR_PERCENTAGE = 0.07f;
    private boolean alternate = false;
    /**
     * Constructor for a ZombieToastSpawner
     * @param position
     */
    public ZombieToastSpawner(Position position) {
        super(TYPE, position);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOverlap(Entity entity, Direction dir) {
        return false;         
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean interact() {
        return destroy();
    }

    /**
     * Destroys the zombieToastSpawner if th eplayer has a weapon (Bow or Shield)
     * @return boolean true if the spawner is destroyed or not
     */
    private boolean destroy(){
        
        List<Collectable> inventory = Dungeon.getInstance().getPlayer().getInventory();
        List<Collectable> weapons = inventory.stream().filter(c -> (c instanceof Sword || c instanceof Bow)).collect(Collectors.toList());

        if (weapons.size() > 0) {
            Dungeon.getInstance().removeEntity(this);
            return true;
        }

        return false;
    }
    private Position getSuitableSpawningPosition() {
        Random rand = Dungeon.getInstance().getRandom();
        List<Position> allPositions = new ArrayList<>();
        for (Position pos : getPosition().getAdjacentFour()){
            if (EntityUtils.getEntitiesAtPosition(pos).size() == 0) {
                allPositions.add(pos);
            }
        }
        if (allPositions.size() == 0) {
            return null;
        }
        return allPositions.get(rand.nextInt(allPositions.size()));
    }
    /**
     * Spawns a zombie toast
     */
    protected void spawnZombieToast() {
        Position spawnPos = getSuitableSpawningPosition();
        if (spawnPos == null) {
            return;
        }
        ZombieToast z = new ZombieToast(spawnPos);
        if (Dungeon.getInstance().getRandom().nextFloat() < ARMOUR_PERCENTAGE) {
            System.out.println("Spawning a Zombie without armour");
            z.getInventory().add(new Armour(getPosition()));
        }
        Dungeon.getInstance().addEntity(z);
    }

    @Override
    public List<AnimationQueue> getAnimationResponse() {
        alternate = !alternate;
        if (alternate) {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite zombie_toast_spawner"), false, -1));
        }
        else {
            animationBuffer.add(new AnimationQueue("PostTick", getId(), Arrays.asList("sprite zombie_toast_spawner_on"), false, -1));
        }
        return super.getAnimationResponse();
    }
}
