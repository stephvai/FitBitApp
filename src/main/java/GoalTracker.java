import java.io.*;

//TODO add image when is achieved

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

			try {
				this.loadProgress();
			} catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
				e.printStackTrace();
			}

        this.updateProgress();

}


/****************************methods
 * @throws IOException
 * @throws ClassNotFoundException *********************************/

/*this method will load the file and store it's progress*/
public void loadProgress() throws IOException, ClassNotFoundException {

        // Input stream to read the file

		FileInputStream	saveFile = new FileInputStream("Pref.ini");


        //restores the variables stored in the object
        ObjectInputStream save = new ObjectInputStream(saveFile);

        //cast to read the object
        this.goalArray = (Goal[]) save.readObject();

        save.close();

}


/////////////////////////////////////////////////////////////////
/*This method will save a user progress*/
public void saveProgress() throws IOException {


            // Opens a file to write to called saveObj
            FileOutputStream saveFile;
			try {
				saveFile = new FileOutputStream("Pref.ini");
			} catch (FileNotFoundException e) {
				File newFile = new File("Pref.ini");
				saveFile = new FileOutputStream(newFile);
			}

            //Creates an ObjectOutputStream to put files into.
            ObjectOutputStream save = new ObjectOutputStream(saveFile);

            //saves all the data to the object
            save.writeObject(this.goalArray);

            //close the outputstream
            save.close();


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
    	return;
    }
    if(type==1){
    	goalObj.setDistance(goal);
    	this.goalArray[1] = goalObj;
    	return;
    }
    if(type==2){
    	goalObj.setCalories(goal);
    	this.goalArray[2] = goalObj;
    	return;
    }
    if(type==3){
    	goalObj.setFloorsClimbed(goal);
    	this.goalArray[3] = goalObj;
    	return;
    }
    if(type==4){
    	goalObj.setActiveMinutes(goal);
    	this.goalArray[4] = goalObj;
    	return;
    }
    if(type==5){
    	goalObj.setSedentaryMinutes(goal);
    	this.goalArray[5] = goalObj;
    	return;
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

    int APIfloors = source.getFloorsClimbed();
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
