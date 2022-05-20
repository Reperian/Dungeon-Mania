package dungeonmania.goals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class CompositeGoal implements Goal {

    protected List<Goal> goals = new ArrayList<>(); // list of subgoals

    /**
     * Constructs a composite goal, storing parameters as subgoals
     * @param goals
     */
    public CompositeGoal(Goal... goals) {

        List<Goal> goalsList = Arrays.asList(goals);

        goalsList.stream().forEach(g -> this.goals.add(g));
    }

    /**
     * Adds a goal to the subgoals
     */
    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = new JsonObject();
        JsonArray childArray = new JsonArray();
        goals.stream().forEach(e -> childArray.add(e.toJsonObject()));
        obj.add("subgoals", childArray);
        return obj;
    }
}
