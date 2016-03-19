package ca.uwo.csd.cs2212.team08;

import java.io.*;

//TODO add image when is achieved

public class GoalTracker implements Serializable {


    private static final long serialVersionUID = 1L;

    /*
    * Instance variables
    */
    private float stepsProgress;
    private float distanceProgress;
    private float caloriesProgress;
    private float floorsClimbedProgress;
    /*
    private float lightlyActiveMinutes;
    private float fairlyActiveMinutes;
    private float veryActiveMinutes;
    */
    private float sedentaryMinutesProgress;


    private Goal[] goalArray;

    /*constructor
    This method will create a Goal array of 6 and load the settings of the user.
    */

    public GoalTracker() throws ClassNotFoundException {
        this.goalArray = new Goal[5];

        goalArray[0].setSteps("0");
        goalArray[1].setCalories("0");
        goalArray[2].setDistance("0");
        goalArray[3].setFloorsClimbed("0");
        goalArray[4].setSedentaryMinutes("0");

        this.loadProgress();

    }


    /****************************
     * methods
     *
     * @throws IOException
     * @throws ClassNotFoundException
     *********************************/

/*this method will load the file and store it's progress*/
    public void loadProgress(){

        // Input stream to read the file
        try {
            FileInputStream saveFile = new FileInputStream("Pref.ini");


            //restores the variables stored in the object
            ObjectInputStream save = null;

                save = new ObjectInputStream(saveFile);


            //cast to read the object

                this.goalArray = (Goal[]) save.readObject();

            save.close();

        }catch (IOException e){
            e.printStackTrace();
            return;
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

    }


    /////////////////////////////////////////////////////////////////
/*This method will save a user progress*/
    public void saveProgress() throws IOException {


        // Opens a file to write to called saveObj
        FileOutputStream saveFile;
        saveFile = new FileOutputStream("Pref.ini");

        //Creates an ObjectOutputStream to put files into.
        ObjectOutputStream save = new ObjectOutputStream(saveFile);

        //saves all the data to the object
        save.writeObject(this.goalArray);

        //close the outputstream
        save.close();


    }
    ///////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////
/*type of GoalsEnum
 * 0 steps
 * 1 distance
 * 2 calorieBurned
 * 3 floorsClimbed
 * 4 activeMinutes
 * 5 sedentaryMinutes
 */


    public void setGoal(String goal, GoalsEnum type) {
        Goal goalObj = new Goal();
        

        if (type == GoalsEnum.steps) { //sets the goal for the steps.
            goalObj.setSteps(goal);
            this.goalArray[0] = goalObj;

        } else if (type == GoalsEnum.distance) {
            goalObj.setDistance(goal);
            this.goalArray[1] = goalObj;

        } else if (type == GoalsEnum.calorieBurned) {
            goalObj.setCalories(goal);
            this.goalArray[2] = goalObj;

        } else if (type == GoalsEnum.floorsClimbed) {
            goalObj.setFloorsClimbed(goal);
            this.goalArray[3] = goalObj;

        } else if (type == GoalsEnum.sedentaryMinutes) {
            goalObj.setSedentaryMinutes(goal);
            this.goalArray[4] = goalObj;

        }
    /*
    else if(type==GoalsEnum.activeMinutes) {
        goalObj.setActiveMinutes(goal);
        this.goalArray[5] = goalObj;
        return;
    }

    else if(type==GoalsEnum.activeMinutes) {
        goalObj.setActiveMinutes(goal);
        this.goalArray[6] = goalObj;
        return;
    }
    else if(type==GoalsEnum.activeMinutes) {
        goalObj.setActiveMinutes(goal);
        this.goalArray[7] = goalObj;
        return;
    }
*/
    }

//////////////////////////////////////////////////////////////////////////


/*This method will update the progress of your goal by seeing if it over 100% and set it to achieved if it is the case */

    public void updateProgress() {

        APIData source = new APIData(); // gather the data

        float APIsteps = source.getSteps();
        if (this.goalArray[0] != null) { //steps goal
            this.stepsProgress = Float.parseFloat( this.goalArray[0].getTarget()) / APIsteps * 100.0f;

            if (this.stepsProgress >= 100)
                this.goalArray[0].setAchieved();
        }

        float APIdistance = (float) source.getDistance();
        if (this.goalArray[1] != null) { //distance goal
            this.distanceProgress = Float.parseFloat(this.goalArray[1].getTarget()) / APIsteps * 100.0f;

            if (this.distanceProgress >= 100)
                this.goalArray[1].setAchieved();
        }


        float APIcalories = source.getCalories();
        if (this.goalArray[2] != null) { //calories goal
            this.caloriesProgress = Float.parseFloat(this.goalArray[2].getTarget()) / APIcalories * 100.0f;

            if (this.caloriesProgress >= 100)
                this.goalArray[2].setAchieved();
        }

        float APIfloors = source.getFloorsClimbed();
        if (this.goalArray[3] != null) { //floors goal
            this.floorsClimbedProgress = Float.parseFloat(this.goalArray[3].getTarget()) / APIfloors * 100.0f;

            if (this.floorsClimbedProgress >= 100)
                this.goalArray[3].setAchieved();
        }

/*
    int APIactiveMinutes = source.getActiveMinutes();
    if (this.goalArray[4]!=null){ //activeMinutes goal
        this.activeMinutesProgress = (float) this.goalArray[4].getTarget() / APIactiveMinutes * 100.0f;

    if (this.activeMinutesProgress >= 100)
        this.goalArray[4].setAchieved();
}
*/
        float APIsedentaryMinutes = source.getSendentaryMinutes();
        if (this.goalArray[4] != null) { //sedentary minutes goal
            this.sedentaryMinutesProgress = Float.parseFloat(this.goalArray[4].getTarget()) / APIsedentaryMinutes * 100.0f;

            if (this.sedentaryMinutesProgress >= 100)
                this.goalArray[4].setAchieved();
        }


/////////////////////////////////////////////////////////////////////

    }

    public Goal getGoal(GoalsEnum type) {

        if (type == GoalsEnum.steps) { //sets the goal for the steps.
            return this.goalArray[0];
        } else if (type == GoalsEnum.distance) {
            return this.goalArray[1];
        } else if (type == GoalsEnum.calorieBurned) {
            return this.goalArray[2];
        } else if (type == GoalsEnum.floorsClimbed) {
            return this.goalArray[3];
        } else if (type == GoalsEnum.sedentaryMinutes) {
            return this.goalArray[4];
        }

        return null;
    }

    /**
     * Helper method to get the array number
     * @param goal
     * @return
     */

    private int  aNum(GoalsEnum goal){
        switch(goal) {
            case steps:
                return 0;

            case distance:
                return 1;

            case calorieBurned:
                return 2;

            case floorsClimbed:
                return 3;

            case sedentaryMinutes:
                return 4;

        }
        return -1;
        // return -1 if something goes wrong
    }



}
