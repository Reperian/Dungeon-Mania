package dungeonmania.dungeon.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.goals.BouldersGoal;
import dungeonmania.goals.CompositeGoal;
import dungeonmania.goals.CompositeGoalAnd;
import dungeonmania.goals.CompositeGoalOr;
import dungeonmania.goals.EnemiesGoal;
import dungeonmania.goals.ExitGoal;
import dungeonmania.goals.Goal;
import dungeonmania.goals.TreasureGoal;

public class GoalLoader {
    /**
     * @precondition type must be valid
     * @param type
     * @return
     */
    public static Goal goalFactory(String type) {
        switch (type) {
            case "AND":
                return new CompositeGoalAnd();
            case "OR":
                return new CompositeGoalOr();
            case "treasure":
                return new TreasureGoal();
            case "enemies":
                return new EnemiesGoal();
            case "boulders":
                return new BouldersGoal();
            case "exit":
                return new ExitGoal();
        }
        return null;
    }
    
    /**
     * Recursively loads all goals as a composite object
     * @param parent
     * @param goal
     * @param subgoals
     * @return
     */
    public static Goal loadGoal(Goal parent, JsonElement goal, JsonElement subgoals) {
        String goalString = goal.getAsString();
        // check if not leaf node
        if (subgoals != null) {
            // Get subgoal array and relevant composite goal from the goal factory
            JsonArray subgoalsArray = subgoals.getAsJsonArray();
            CompositeGoal compositeGoal = (CompositeGoal) goalFactory(goalString);
            // Adds child nodes to that composite goal via recursion
            for (JsonElement element : subgoalsArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                compositeGoal.addGoal(loadGoal(compositeGoal, jsonObject.get("goal"), jsonObject.get("subgoals")));
            }
            return compositeGoal;
        }
        return goalFactory(goalString);
    }
}
