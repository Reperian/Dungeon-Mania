package dungeonmania.goals;

import com.google.gson.JsonObject;

public interface Goal {
    
    /**
     * Checks whether the goal has been completed, should be run on every tick and interaction
     * @return empty string if goal has been completed, otherwise return the string of the goal (either singular or composite) that is not completed
     */
    public String checkGoal();

    public JsonObject toJsonObject();
}
