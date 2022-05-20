package dungeonmania.util;

import java.util.*;

import dungeonmania.dungeon.Dungeon;
import dungeonmania.entities.Entity;

public class GetEntities {

    public static List<Entity> getEntityList(Class<?> type) {
        Dungeon d = Dungeon.getInstance();
        List<Entity> newList = new ArrayList<Entity>();
        for (Entity e : d.getEntities()) {
            if (e.getClass().equals(type)) {
                newList.add(e);
            }
        }
        return newList;
    }
}
