import java.io.*;

//TODO add image when is achieved
//TODO add exception handling

public class GoalTracker {

    /*
    * Instance variables
    */
    private float stepsProgress;
    private float distanceProgress;
    private float caloriesProgress;
    private float floorsClimbedProgress;
    private float activeMinutesProgress;
    private float sedentaryMinutesProgress;

    private Goal[] goalArray;

    /*constructor
    This method will create a Goal array of 6 and load the settings of the user.
    */
    
    public  GoalTracker() {
        this.goalArray = new Goal[6];
        this.loadProgress();
        this.updateProgress();

}


/****************************methods *********************************/

/*this method will load the file and store it's progress*/
public void loadProgress() {

        // Input stream to read the file
        FileInputStream saveFile;
		try {
			saveFile = new FileInputStream("Pref.ini");
		} catch (FileNotFoundException e) {
			throw new Exception(); //TODO FIX Exception handling
		}

        //restores the variables stored in the object
        ObjectInputStream save = new ObjectInputStream(saveFile);

        //cast to read the object
        this.goalArray = (Goal[]) save.readObject();

        save.close();
   
    //TODO add exception in case of null file.
}


/////////////////////////////////////////////////////////////////
/*This method will save a user progress*/
public void saveProgress() {


            // Opens a file to write to called saveObj
            FileOutputStream saveFile = new FileOutputStream("Pref.ini");

            //Creates an ObjectOutputStream to put files into.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            //saves all the data to the object
            save.writeObject(this.goalArray);

            //close the outputstream
            save.close();

        //TODO add null exception handler

    }
    ///////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////
/*type of goals
 * 0 steps
 * 1 distance
 * 2 calorieBurned
 * 3 floorsClimbed
 * 4 activeMinutes
 * 5 sedentaryMinutes
 */
public void setGoal(int goal, int type) {
    Goal goalObj = new Goal();
    
    if(type ==0){ //sets the goal for the steps.
    	goalObj.setSteps(goal);
    	this.goalArray[0] = goalObj;
    }
    if(type==1){
    	goalObj.setDistance(goal);
    	this.goalArray[1] = goalObj;
    }
    if(type==2){
    	goalObj.setCalories(goal);
    	this.goalArray[2] = goalObj;
    }
    if(type==3){
    	goalObj.setFloorsClimbed(goal);
    	this.goalArray[3] = goalObj;
    }
    if(type==4){
    	goalObj.setActiveMinutes(goal);
    	this.goalArray[4] = goalObj;
    }
    if(type==5){
    	goalObj.setSedentaryMinutes(goal);
    	this.goalArray[5] = goalObj;
    }
    	
}

//////////////////////////////////////////////////////////////////////////


/*This method will update the progress of your goal by seeing if it over 100% and set it to achieved if it is the case */

public void updateProgress() {

    APIData source = new APIData(); // gather the data

    int APIsteps = source.getSteps();
    if (this.goalArray[0] !=null) { //steps goal
        this.stepsProgress =  (float) this.goalArray[0].getTarget() / APIsteps * 100.0f;

        if (this.stepsProgress >= 100)
            this.goalArray[0].setAchieved();
    }

    int APIdistance = source.getDistance();
    if (this.goalArray[1]!=null) { //distance goal
        this.distanceProgress = (float) this.goalArray[1].getTarget() / APIsteps * 100.0f;

        if (this.distanceProgress >= 100)
            this.goalArray[1].setAchieved();
    }


    int APIcalories = source.getCalories();
    if (this.goalArray[2]!=null) { //calories goal
        this.caloriesProgress = (float) this.goalArray[2].getTarget() / APIcalories * 100.0f;

        if (this.caloriesProgress >= 100)
            this.goalArray[2].setAchieved();
    }

    int APIfloors = source.floorsClimbed();
    if (this.goalArray[3]!=null) { //floors goal
        this.floorsClimbedProgress = (float) this.goalArray[3].getTarget() / APIfloors * 100.0f;

        if (this.floorsClimbedProgress >= 100)
            this.goalArray[3].setAchieved();
    }


    int APIactiveMinutes = source.getActiveMinutes();
    if (this.goalArray[4]!=null){ //activeMinutes goal
        this.activeMinutesProgress = (float) this.goalArray[4].getTarget() / APIactiveMinutes * 100.0f;

    if (this.activeMinutesProgress >= 100)
        this.goalArray[4].setAchieved();
}

int APIsedentaryMinutes = source.getSendentaryMinutes();
if (this.goalArray[5]!=null) { //sedentary minutes goal
    this.sedentaryMinutesProgress = (float) this.goalArray[5].getTarget() / APIsedentaryMinutes * 100.0f;

    if (this.sedentaryMinutesProgress >= 100)
        this.goalArray[5].setAchieved();
}


/////////////////////////////////////////////////////////////////////




}


}