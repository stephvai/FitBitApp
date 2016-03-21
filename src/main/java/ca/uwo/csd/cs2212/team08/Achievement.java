package ca.uwo.csd.cs2212.team08;

import java.io.Serializable;

//TODO add images for the accolades

public class Achievement implements Serializable {

    private static final long serialVersionUID = -8064307587743333279L;
    private String type;
    private boolean achieved;
    private float objective;

    public Achievement(){
        this.achieved = false;
    }//Constructor.

    /*Getters*/

    public boolean getAchieved(){return this. achieved;}//Return true if the achievement is achieved.

    public String getType(){return this.type;}//Return the type of the achievement.

    public float getObjective(){return this.objective;}

    /*Setters*/
    public void setType(String s){
        this.type = s;
    }
    public void setAchieved(){
        this.achieved = true;
    }
    /*
    public void setObjective(float obj){
        this.objective = obj;
    }
    */

    public void setCalories(float target){
        this.type = "calories";
        this.objective = target;
    }

    public void setSteps(float target){
        this.type = "steps";
        this.objective = target;
    }

    public void setFloorsClimbed(float target){
        this.type = "floorsclimbed";
        this.objective = target;
    }

    public void setDistance(float target){
        this.type = "distance";
        this.objective = target;
    }
}
