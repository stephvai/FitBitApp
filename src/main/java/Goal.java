//TODO add achieved and non achieved image

public class Goal {

    private int target;
    private boolean achieved;
    private String type;

    /*This constructor initializes the object and sets the target goal */
    public Goal() {
        achieved = false;
    }

    /*Getters and Setters */
    public int getTarget() {
        return target;
    }


    public boolean getAchieved() {
        return achieved;
    }

    public void setAchieved() {
        this.achieved = true;
    }

    public String getType(){
    	return type;
    }
    
    /**************************************************************
    These setters will set the goal and the type 
    ***************************************************************/
    
    public void setCalories(int target){
    	this.type= "calories";
    	this.target = target;
    }
    
    public void setSteps(int target){
    	this.type= "steps";
    	this.target = target;
    }
    
    public void setFloorsClimbed(int target){
    	this.type= "floorsclimbed";
    	this.target = target;
    }
    
    public void setActiveMinutes(int target){
    	this.type= "activeminutes";
    	this.target = target;
    }
    
    public void setSedentaryMinutes(int target){
    	this.type= "sedentaryminutes";
    	this.target = target;
    }
    
    public void setDistance(int target){
    	this.type= "distance";
    	this.target = target;
    }

}