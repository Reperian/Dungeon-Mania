package dungeonmania.goals;

import com.google.gson.JsonObject;

public class CompositeGoalAnd extends CompositeGoal {

    /**
     * Constructs a composite goal where all sub goals need to be completed to pass the composite goal
     */
    public CompositeGoalAnd(Goal... goals) {
        
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
           
            if (!goal.checkGoal().equals("") && goal_string.equals("")) { // add the subgoal to the goal string if it is incomplete

                goal_string = String.format("%s", goal.checkGoal());

            } else if (!goal.checkGoal().equals("")) { // add an 'AND' and the uncompleted subgoal to the goal string to be returned

                goal_string = String.format("%s AND %s", goal_string, goal.checkGoal());
            } 
        }

        return goal_string;
    }
    @Override
    public JsonObject toJsonObject() {
        JsonObject obj = super.toJsonObject();
        obj.addProperty("goal", "AND");
        return obj;
    }
}
