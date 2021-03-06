package ca.uwo.csd.cs2212.team08;

import java.io.*;

//TODO add image when is achieved
/**
 * a class that is used to track the users goals
 */
public class GoalTracker implements Serializable {


    private static final long serialVersionUID = 1L;

    /*
    * Instance variables
    */
    private float stepsProgress;
    private float distanceProgress;
    private float caloriesProgress;
    private float floorsClimbedProgress;
    private float activeMinutesProgress;

    private APIData source;
    private Goal[] goalArray;


    /**
     * This method will create a Goal array of 6 and load the settings of the user.
     * @param source the apidata that you can pass in
     * @throws ClassNotFoundException
     */
    public GoalTracker(APIData source) throws ClassNotFoundException {
        this.goalArray = new Goal[5];

        this.source = source;

        for(int i = 0; i< 5; i++){
            goalArray[i]= new Goal();
        }

        goalArray[0].setSteps("0");
        goalArray[1].setCalories("0");
        goalArray[2].setDistance("0");
        goalArray[3].setFloorsClimbed("0");
        goalArray[4].setVeryActiveMinutes("0");

        this.loadProgress();
        updateProgress();

    }


    /****************************
     * methods
     *
     * @throws IOException
     * @throws ClassNotFoundException
     *********************************/

    /**
     * This method will load the file and store it's progress
     */
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
            //e.printStackTrace();
            return;
        }catch (ClassNotFoundException e) {
            //e.printStackTrace();
            return;
        }

    }


    /////////////////////////////////////////////////////////////////
    /**
     * this method will save the user progress
     * @throws IOException
     */
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

    /**
     * set the goals that the user created
     * @param goal pass in the goal value
     * @param type pass in the type of the goal
     */
    public void setGoal(String goal, GoalsEnum type) {
        Goal goalObj = new Goal();


        if (type == GoalsEnum.steps) { //sets the goal for the steps.
            goalObj.setSteps(goal);
            this.goalArray[0].setSteps(goal);
            updateProgress();
            try {
                saveProgress();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return;

        } else if (type == GoalsEnum.distance) {
            this.goalArray[1].setCalories(goal);
            updateProgress();
            try {
                saveProgress();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return;

        } else if (type == GoalsEnum.calorieBurned) {
            this.goalArray[2].setDistance(goal);
            updateProgress();
            try {
                saveProgress();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return;

        } else if (type == GoalsEnum.floorsClimbed) {
            this.goalArray[3].setFloorsClimbed(goal);
            updateProgress();
            try {
                saveProgress();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return;

        } else if (type == GoalsEnum.veryActiveMinutes) {
            this.goalArray[4].setVeryActiveMinutes(goal);
            updateProgress();
            try {
                saveProgress();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            return;

        }
    }

    /**
     * steps
     * distance
     * caloriesBurned
     * floorsCLimbed
     * veryActiveMinutes
     */

//////////////////////////////////////////////////////////////////////////


    /**
     * steps
     * distance
     * caloriesBurned
     * floorsCLimbed
     * veryActiveMinutes
     */

    
    /**
     * this method will update the progress of your goal by seeing if it over 100% and set it to achieved if it is the case
     */
    public void updateProgress() {


        float APIsteps = source.getSteps();
        if (this.goalArray[0].getTarget() != "0" ) { //steps goal
            this.stepsProgress = APIsteps / Float.parseFloat( this.goalArray[0].getTarget()) * 100.0f;

            if (this.stepsProgress >= 100)
                this.goalArray[0].setAchieved();
        }

        float APIdistance =  source.getDistance();
        if (this.goalArray[1].getTarget() != "0") { //distance goal
            this.distanceProgress = APIdistance / Float.parseFloat(this.goalArray[1].getTarget()) * 100.0f;

            if (this.distanceProgress >= 100)
                this.goalArray[1].setAchieved();
        }


        float APIcalories = source.getCalories();
        if (this.goalArray[2].getTarget() != "0") { //calories goal
            this.caloriesProgress = APIcalories / Float.parseFloat(this.goalArray[2].getTarget()) * 100.0f;

            if (this.caloriesProgress >= 100)
                this.goalArray[2].setAchieved();
        }

        float APIfloors = source.getFloorsClimbed();
        if (this.goalArray[3].getTarget() != "0") { //floors goal
            this.floorsClimbedProgress = APIfloors / Float.parseFloat(this.goalArray[3].getTarget()) * 100.0f;

            if (this.floorsClimbedProgress >= 100)
                this.goalArray[3].setAchieved();
        }

        float APIActiveMinutes = source.getVeryActiveMin() + source.getLightlyActiveMin() + source.getFairlyActiveMin();
        if (this.goalArray[4].getTarget() != "0") { //sedentary minutes goal
            this.activeMinutesProgress = APIActiveMinutes / Float.parseFloat(this.goalArray[4].getTarget()) * 100.0f;

            if (this.activeMinutesProgress >= 100)
                this.goalArray[4].setAchieved();
        }


/////////////////////////////////////////////////////////////////////

    }



    /**
     * steps
     * distance
     * caloriesBurned
     * floorsCLimbed
     * veryActiveMinutes
     */
    
    /**
     * gets the goal for a specific goal type
     * @param type pas in the type of goal
     * @return the value of the goal you are looking for
     */
    public String getGoal(GoalsEnum type) {

        if (type == GoalsEnum.steps) { //sets the goal for the steps.
            return this.goalArray[0].getTarget();
        } else if (type == GoalsEnum.distance) {
            return this.goalArray[1].getTarget();
        } else if (type == GoalsEnum.calorieBurned) {
            return this.goalArray[2].getTarget();
        } else if (type == GoalsEnum.floorsClimbed) {
            return this.goalArray[3].getTarget();
        } else if (type == GoalsEnum.veryActiveMinutes) {
            return this.goalArray[4].getTarget();
        }

        return null;
    }

    /**
     * @return the steps progress
     */
    public float getStepsProgress() {
        return stepsProgress;
    }

    /**
     * 
     * @return the distance progress
     */
    public float getDistanceProgress() {
        return distanceProgress;
    }

    /**
     * 
     * @return the calories progress
     */
    public float getCaloriesProgress() {
        return caloriesProgress;
    }

    /**
     * 
     * @return the floors climbed progress
     */
    public float getFloorsClimbedProgress() {
        return floorsClimbedProgress;
    }

    /**
     * 
     * @return returns the active min progress
     */
    public float getVeryActiveMinutesProgress() {
        return activeMinutesProgress;
    }


    /**
     * Helper method to get the array number
     * @param goal
     * @return
     */




}
