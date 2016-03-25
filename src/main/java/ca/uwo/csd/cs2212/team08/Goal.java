package ca.uwo.csd.cs2212.team08;

import java.io.Serializable;

//TODO add achieved and non achieved image

/**
 * creates a class to store a goal object
 */
public class Goal implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String target;
    private boolean achieved;
    private String type;

    /**
     * This constructor initializes the object and sets the target goal
     */
    public Goal() {
        achieved = false;
    }

    /*Getters and Setters */
    /**
     * @return the goal target
     */
    public String getTarget() {
        return target;
    }

    /**
     * 
     * @return get if the goal is achieved
     */
    public boolean getAchieved() {
        return achieved;
    }

    /**
     * set the goal to achieved or not
     */
    public void setAchieved() {
        this.achieved = true;
    }

    /**
     * @return returns the type of the goal
     */
    public String getType(){
    	return type;
    }
    
    /**************************************************************
    These setters will set the goal and the type 
    ***************************************************************/
    
    /**
     * set the calories goal 
     * @param target is the target of the goal you want to make
     */
    public void setCalories(String target){
    	this.type= "calories";
    	this.target = target;
    }
    
    /**
     * set the steps goal 
     * @param target is the target of the goal you want to make 
     */
    public void setSteps(String target){
    	this.type= "steps";
    	this.target = target;
    }
    
    /**
     * set the floors goal
     * @param target is the target of the goal you want to make
     */
    public void setFloorsClimbed(String target){
    	this.type= "floorsclimbed";
    	this.target = target;
    }

    /**
     * set the active min goals
     * @param target is the target of the goal you want to make
     */
    public void setVeryActiveMinutes(String target){
        this.type= "activeminutes";
        this.target = target;
    }

    /**
     * set the distance goal
     * @param target is the target of the goal you want to make
     */
    public void setDistance(String target){
    	this.type= "distance";
    	this.target = target;
    }

}