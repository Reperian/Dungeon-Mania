package dungeonmania.dungeon.util;

import dungeonmania.util.Position;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.anduril.Anduril;
import dungeonmania.entities.armour.Armour;
import dungeonmania.entities.arrows.Arrows;
import dungeonmania.entities.bomb.Bomb;
import dungeonmania.entities.bomb.PlacedBomb;
import dungeonmania.entities.player.Player;
import dungeonmania.entities.portal.Portal;
import dungeonmania.entities.sceptre.Sceptre;
import dungeonmania.entities.shield.Shield;
import dungeonmania.entities.sunstone.SunStone;
import dungeonmania.entities.swamp_tile.SwampTile;
import dungeonmania.entities.moving_entities.Spider;
import dungeonmania.entities.sword.Sword;
import dungeonmania.entities.the_one_ring.TheOneRing;
import dungeonmania.entities.treasure.Treasure;
import dungeonmania.entities.unimplemented.UnimplementedEntity;
import dungeonmania.entities.wall.Wall;
import dungeonmania.entities.wood.Wood;
import dungeonmania.entities.zombie_toast_spawner.ZombieToastSpawnerFactory;
import dungeonmania.entities.moving_entities.ZombieToast;
import dungeonmania.entities.boulder.Boulder;
import dungeonmania.entities.bow.Bow;
import dungeonmania.entities.door.LockedDoor;
import dungeonmania.entities.door.UnlockedDoor;
import dungeonmania.entities.exit.Exit;
import dungeonmania.entities.floor_switch.FloorSwitch;
import dungeonmania.entities.health_potion.HealthPotion;
import dungeonmania.entities.invinciblity_potion.InvincibilityPotion;
import dungeonmania.entities.invisibility_potion.InvisibilityPotion;
import dungeonmania.entities.key.Key;
import dungeonmania.entities.midnight_armour.MidnightArmour;
import dungeonmania.entities.moving_entities.Assassin;
import dungeonmania.entities.moving_entities.FriendlyAssassin;
import dungeonmania.entities.moving_entities.FriendlyMercenary;
import dungeonmania.entities.moving_entities.Hydra;
import dungeonmania.entities.moving_entities.Mercenary;

public class EntityLoader {
    /**
     * Takes in an json object and gamemode and returns the relavant entity
     * @param entity
     * @param gameMode
     * @return Entity
     */
    public static Entity getEntity(JsonObject entity, String gameMode) {
        int x = entity.get("x").getAsInt();
        int y = entity.get("y").getAsInt();

        String type = entity.get("type").getAsString();
        
        JsonElement durability = entity.get("durability");
        
        switch (type) {
            case "armour":
                Armour armour = new Armour(new Position(x, y));;
                if (durability != null) {
                    armour.setDurability(durability.getAsInt());
                }
                return armour;
            case "arrow":
                return new Arrows(new Position(x, y, 1));
            case "bomb":
                return new Bomb(new Position(x, y, 1));
            case "bomb_placed":
                return new PlacedBomb(new Position(x, y, 1));
            case "boulder":
                return new Boulder(new Position(x, y, 1));
            case "bow":
                Bow bow = new Bow(new Position(x, y, 1));
                if (durability != null) {
                    bow.setDurability(durability.getAsInt());
                }
                return bow;
            case "door_locked":
                return new LockedDoor(new Position(x, y, 0), entity.get("key").getAsInt());
            case "door_unlocked":
                return new UnlockedDoor(new Position(x, y, 0), entity.get("key").getAsInt());
            case "exit":
                return new Exit(new Position(x, y, 0));
            case "switch":
                return new FloorSwitch(new Position(x, y, 0));
            case "health_potion":
                return new HealthPotion(new Position(x, y, 1));
            case "invincibility_potion":
                return new InvincibilityPotion(new Position(x, y, 1));
            case "invisibility_potion":
                return new InvisibilityPotion(new Position(x, y, 1));
            case "key":
                return new Key(new Position(x, y, 1), entity.get("key").getAsInt());
            case "mercenary_friendly":
                FriendlyMercenary friendlyMercenary = new FriendlyMercenary(new Position(x, y, 2));
                friendlyMercenary.setInventory(InventoryLoader.getInventory(entity, gameMode));
            return friendlyMercenary;
            case "mercenary":
                Mercenary mercenary = new Mercenary(new Position(x, y, 2));
                mercenary.setInventory(InventoryLoader.getInventory(entity, gameMode));
                return mercenary;
            case "assassin":
                Assassin assassin = new Assassin(new Position(x, y, 2));
                assassin.setInventory(InventoryLoader.getInventory(entity, gameMode));
                return assassin;
            case "assassin_friendly":
                FriendlyAssassin friendlyassassin = new FriendlyAssassin(new Position(x, y, 2));
                friendlyassassin.setInventory(InventoryLoader.getInventory(entity, gameMode));
                return friendlyassassin;
            case "player":
                Player player = new Player(new Position(x, y, 4));
                JsonElement healthElement = entity.get("health");
                if (healthElement != null) {
                    player.setHealth(healthElement.getAsInt());
                }
                player.setInventory(InventoryLoader.getInventory(entity, gameMode));
                return player;
            case "portal":
                return new Portal(new Position(x, y, 0), entity.get("colour").getAsString());
            case "shield":
                Shield shield = new Shield(new Position(x, y, 1));
                if (durability != null) {
                    shield.setDurability(durability.getAsInt());
                }
                return shield;
            case "spider":
                return new Spider(new Position(x, y, 2));
            case "hydra":
                return new Hydra(new Position(x, y, 2));
            case "sword":
                Sword sword = new Sword(new Position(x, y, 1));
                if (durability != null) {
                    sword.setDurability(durability.getAsInt());
                }
                return sword;
            case "the_one_ring":
                return new TheOneRing(new Position(x, y, 1));
            case "treasure":
                return new Treasure(new Position(x, y, 1));
            case "wall":
                return new Wall(new Position(x, y, 0));
            case "wood":
                return new Wood(new Position(x, y, 1));
            case "zombie_toast":
                ZombieToast zombieToast = new ZombieToast(new Position(x, y, 2));
                zombieToast.setInventory(InventoryLoader.getInventory(entity, gameMode));
                return new ZombieToast(new Position(x, y));
            case "zombie_toast_spawner":
                return ZombieToastSpawnerFactory.getSpawner(new Position(x, y, 3), gameMode);
            case "anduril":
                return new Anduril(new Position(x, y, 1));    
            case "sun_stone":
                return new SunStone(new Position(x, y, 1));
            case "sceptre":
                return new Sceptre(new Position(x, y, 1));
            case "midnight_armour":
                return new MidnightArmour(new Position(x, y, 1));
            case "swamp_tile":
                return new SwampTile(new Position(x, y, 0), entity.get("movement_factor").getAsInt());
            default:
                return new UnimplementedEntity(new Position(x, y));
        }
    }
}
