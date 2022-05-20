package dungeonmania.entities.moving_entities.spawn;

import java.util.List;
import java.util.Random;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.moving_entities.Assassin;
import dungeonmania.entities.moving_entities.Hydra;
import dungeonmania.entities.moving_entities.Mercenary;
import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.entities.moving_entities.spider.PeacefulSpider;
import dungeonmania.entities.moving_entities.spider.Util;
import dungeonmania.entities.util.EntityUtils;
import dungeonmania.util.Position;

public class MovingEntitySpawner {
    private static final int STANDARD_ZOMBIE_RATE = 20;
    private static final int HARD_ZOMBIE_RATE = 15;

    public static void Spawn(String gameMode, int tick) {
        List<Entity> entities = Dungeon.getInstance().getEntities();
        int spiders = EntityUtils.getEntitiesToTypeList(entities, Spider.class).size();
        if (tick % (gameMode.equals("Hard") ? HARD_ZOMBIE_RATE : STANDARD_ZOMBIE_RATE) == 0 && spiders < 4) {
            spiderSpawn(gameMode, entities);
        }

        if (tick % 50 == 0 && gameMode.equals("Hard")) {
            hydraSpawn(gameMode, entities);
        }

        if (tick % 50 == 0) {
            mercenarySpawn(gameMode, entities);
        }
	}

    public static void spiderSpawn(String gameMode, List<Entity> entities) {

        Position spawnLocation = Util.getRandomValidPosition();
        if (spawnLocation == null) {
            return;
        }
        else {
            switch(gameMode) {
                case("Standard"):
                    entities.add(new Spider(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Peaceful"):
                    entities.add(new PeacefulSpider(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Hard"):
                    entities.add(new Spider(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));    
                    return;
                default:
                    new IllegalArgumentException("Invalid Game Mode");
            }
        }


    }

    public static void hydraSpawn(String gameMode, List<Entity> entities) {

        Position spawnLocation = Util.getRandomValidPosition();

        if (spawnLocation == null) {
            return;
        }
        else {
            switch(gameMode) {
                case("Standard"):
                    return;
                case("Peaceful"):
                    return;
                case("Hard"):
                    entities.add(new Hydra(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));    
                    return;
                default:
                    new IllegalArgumentException("Invalid Game Mode");
            }
        }
    }

    public static void mercenarySpawn(String gameMode, List<Entity> entities) {

        Position spawnLocation = Dungeon.getInstance().getEntry();
        Random rand = Dungeon.getInstance().getRandom();

        if (rand.nextInt(100) < 30) {
            switch(gameMode) {
                case("Standard"):
                    entities.add(new Assassin(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Peaceful"):
                    entities.add(new Assassin(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Hard"):
                    entities.add(new Assassin(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));    
                    return;
                default:
                    new IllegalArgumentException("Invalid Game Mode");
            }
        }
        else {
            switch(gameMode) {
                case("Standard"):
                    entities.add(new Mercenary(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Peaceful"):
                    entities.add(new Mercenary(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));
                    return;
                case("Hard"):
                    entities.add(new Mercenary(new Position(spawnLocation.getX(), spawnLocation.getY(), 2)));    
                    return;
                default:
                    new IllegalArgumentException("Invalid Game Mode");
            }
        }


    }
}
