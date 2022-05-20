package dungeonmania.dungeon.util;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.behaviours.Collectable;
import dungeonmania.entities.bow.Bow;
import dungeonmania.entities.midnight_armour.MidnightArmour;
import dungeonmania.entities.sceptre.Sceptre;
import dungeonmania.entities.shield.Shield;
import dungeonmania.exceptions.InvalidActionException;

public class BuildableCreator {
    
    /**
     * Constructs buildable entities
     * @param buildable name of the entity to build
     * @return the built entity as a collectable
     * @throws IllegalArgumentException if the name of the entity to build is invalid
     * @throws InvalidActionException if the player doesn't have sufficient items in their inventory to build the entity
     */
    public static Collectable BuildableFactory(String buildable) throws IllegalArgumentException, InvalidActionException {

        switch (buildable) {

            case "bow":
                
                return new Bow();
                
            case "shield":

                return new Shield();

            case "sceptre":
                
                return new Sceptre();
                
            case "midnight_armour":

                return new MidnightArmour();
            
            default:

                throw new IllegalArgumentException("'Buildable' is not a buildable item");
                
        }

    }

    /**
     * Generates a list of entities that can be built from items in the player's inventory
     * @return list of buildable entities as strings
     */
    public static List<String> generateBuildables() {

        List<String> buildables = new ArrayList<String>();

        if (Bow.isBuildable()) {

            buildables.add("bow");

        }
        
        if (Shield.isBuildable()) {

            buildables.add("shield");

        }

        if (Sceptre.isBuildable()) {

            buildables.add("sceptre");

        }
        
        if (MidnightArmour.isBuildable()) {

            buildables.add("midnight_armour");

        }

        return buildables;

    }

}
