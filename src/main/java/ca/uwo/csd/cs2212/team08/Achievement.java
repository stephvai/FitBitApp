package ca.uwo.csd.cs2212.team08;

import java.io.Serializable;

/**
 * A class that represents a singular achievement to be used with the achivement tracker
 */
public class Achievement implements Serializable {

    private static final long serialVersionUID = -8064307587743333279L;
    private String type;
    private boolean achieved;
    private float objective;
    
    /**
     * Constructor for an achievement
     */
    public Achievement(){
        this.achieved = false;
    }

    
    /**
     * getter that returns the achievement status
     * @return if the achievement is achieved by the user
     */
    public boolean getAchieved(){return this. achieved;}//Return true if the achievement is achieved.
    
    /**
     * getter that returns the type of achievement
     * @return the type of achievement
     */
    public String getType(){return this.type;}//Return the type of the achievement.

    /**
     * getter for the objective of the achievement
     * @return the objective of the achievement
     */
    public float getObjective(){return this.objective;}

    /*Setters*/
    /**
     * Setter for the type of achievement
     * @param s the string to set the type of achievement to
     */
    public void setType(String s){
        this.type = s;
    }
    /**
     * sets that the achievement has been achieved
     */
    public void setAchieved(){
        this.achieved = true;
    }
    /*
    public void setObjective(float obj){
        this.objective = obj;
    }
    */
    /**
     * sets the calorie objective
     * @param target the objective to reach
     */
    public void setCalories(float target){
        this.type = "calories";
        this.objective = target;
    }

    /**
     * sets the steps objective
     * @param target the objective to reach
     */
    public void setSteps(float target){
        this.type = "steps";
        this.objective = target;
    }
    
    /**
     * sets the floors objective
     * @param target the objective to reach
     */
    public void setFloorsClimbed(float target){
        this.type = "floorsclimbed";
        this.objective = target;
    }

    /**
     * sets the distance objective
     * @param target the objective to reach
     */
    public void setDistance(float target){
        this.type = "distance";
        this.objective = target;
    }
}
