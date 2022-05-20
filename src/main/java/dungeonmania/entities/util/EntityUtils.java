package dungeonmania.entities.util;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;
import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.util.Position;
import java.lang.Math;

public class EntityUtils {
    /**
     * Filters the entity list for type T and returns a list containing entities casted to
     * that type.
     * Usage: List<T> subList = EntityUtils.getEntitiesToTypeList(list, type.class)
     * @param <T>
     * @param list
     * @param type
     * @return
     */
    public static <T>List<T> getEntitiesToTypeList(List<Entity> list, Class<T> type) {
        List<T> filteredEntities = list.stream()
                            .filter(e -> type.isInstance(e))
                            .map(e -> type.cast(e))
                            .collect(Collectors.toList());
        return filteredEntities;
    }

    /**
     * Filters the entity list for a given type and returns a list of all entities of that
     * type.
     * Usage: List<Entity> subList = EntityUtils.getEntitiesOfTypeList(list, type.class)
     * @param list
     * @param type
     * @return
     */
    public static List<Entity> getEntitiesOfTypeList(List<Entity> list, Class<?> type) {
        List<Entity> filteredEntities = list.stream()
                            .filter(e -> (type.isInstance(e)))
                            .collect(Collectors.toList());
        return filteredEntities;
    }

    /**
     * Returns a list of all entities in a given position
     * Usage: List<Entity> subList = EntityUtils.getEntitiesAtPosition(position)
     * @param position
     * @return list of entities
     */
    public static List<Entity> getEntitiesAtPosition(Position position) {
        List<Entity> entities = Dungeon.getInstance().getEntities();
        
        List<Entity> entitiesAtPosition = entities.stream()
                                                  .filter(e -> (e.getPosition().equals(position)))
                                                  .collect(Collectors.toList());
        
        return entitiesAtPosition;
    }

    /**
     * Returns a list of all entities in a given position
     * Usage: List<Entity> subList = EntityUtils.getEntitiesAtPosition(position)
     * @param position
     * @return list of entities
     */
    public static Entity getEntityFromId(String id) throws IllegalArgumentException{
        List<Entity> entities = Dungeon.getInstance().getEntities();
        
        List<Entity> entity = entities.stream()
                                      .filter(e -> (e.getId().equals(id)))
                                      .collect(Collectors.toList());
        
        if (entity.size() == 0) {
            throw new IllegalArgumentException("Given entity id does not exist");
        }

        return entity.get(0);
    }

    /**
     */
    public static boolean isAdjacent(Position a, Position b) {
        int x = a.getX()- b.getX();
        int y = a.getY() - b.getY();
        return Math.abs(x + y) == 1;
    }

    /**
     */
    public static boolean isWithinRadius(Position a, Position b, int radius) {   
        int x = a.getX()- b.getX();
        int y = a.getY() - b.getY();
        return Math.abs(x + y) <= radius;
    }

     /**
     * Filters the Collectable list of inventory for type T and returns a list containing Collectable casted to
     * that type.
     * Usage: List<T> subList = EntityUtils.getInventoryToTypeList(list, type.class)
     * @param <T>
     * @param list
     * @param type
     * @return
     */
    public static <T>List<T> getInventoryToTypeList(List<Collectable> list, Class<T> type) {
        List<T> filteredEntities = list.stream()
                            .filter(e -> type.isInstance(e))
                            .map(e -> type.cast(e))
                            .collect(Collectors.toList());
        return filteredEntities;
    }
    /**
     * Filters the Collectable list for a given type and returns a list of all entities of that
     * type.
     * Usage: List<Entity> subList = EntityUtils.getEntitiesOfTypeList(list, type.class)
     * @param list
     * @param type
     * @return
     */
    public static List<Entity> getEntitiesFromInventory(List<Collectable> list, Class<?> type) {
        List<Entity> filteredEntities = list.stream()
                            .filter(e -> (type.isInstance(e)))
                            .map(e -> Entity.class.cast(e))
                            .collect(Collectors.toList());
        return filteredEntities;
    }
    /**
     * Filters the Collectable list for a given type and returns a list of all entities of that
     * type.
     * Usage: List<Entity> subList = EntityUtils.getEntitiesOfTypeList(list, type.class)
     * @param list
     * @param type
     * @return
     */
    public static List<Collectable> getCollectablesFromInventory(List<Collectable> list, Class<?> type) {
        List<Collectable> filteredEntities = list.stream()
                            .filter(e -> (type.isInstance(e)))
                            .collect(Collectors.toList());
        return filteredEntities;
    }

    
    public static Entity getEntityFromInventoryId(List<Collectable> list, String id) {
        try {
            return (Entity)list.stream()
                                .filter(e -> ((Entity)e).getId().equals(id))
                                .findAny().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
