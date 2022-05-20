package dungeonmania.goals;

import com.google.gson.JsonObject;

public class CompositeGoalOr extends CompositeGoal {

    /**
     * Constructs a composite goal where only one sub goal need to be completed to pass the composite goal
     */
    public CompositeGoalOr(Goal... goals) {
        
        super(goals);
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkGoal() {
        
        String goal_string = "";

        // generating the goal string to be returned

        for (Goal goal: goals) {
            
            if (goal.checkGoal().equals("")) { // if a subgoal is completed return an empty string as the composite goal has been completed

                return "";

            } else if (!goal.checkGoal().equals("") && goal_string.equals("")) { // add the uncompleted subgoal to the goal string to be returned

                goal_string = String.format("%s", goal.checkGoal());

            } else { // add an 'OR' and the uncompleted subgoal to the goal string to be returned

                goal_string = String.format("%s OR %s", goal_string, goal.checkGoal());
            }
        }

        return goal_string;
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = super.toJsonObject();
        obj.addProperty("goal", "OR");
        return obj;
    }
}
