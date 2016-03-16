package ca.uwo.csd.cs2212.team08;

import java.io.Serializable;

//TODO add images for the accolades

public class Achievement implements Serializable {

    private String type;
    private boolean achieved;
    private float objective;

    public Achievement(){
        this.achieved = false;
    }//Constructor.

    /*Getters*/
    public boolean getAchieved(){return this. achieved;}

    public String getType(){return this.type;}

    public float getObjective(){return this.objective;}

    /*Setters*/
    public void setType(String s){
        this.type = s;
    }
    public void setAchieved(){
        this.achieved = true;
    }
    public void setObjective(float obj){
        this.objective = obj;
    }
}
