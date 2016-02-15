import java.io.*;

public class GoalTracker {

/*
***********************************************************
 Store these types in the following array locations 
	steps; //0
	distance; //1
	caloriesBurned; //2
	floorsClimbed; //3
	activeMinutes;//4
	sedentaryMinutes; //5
*********************************************************
 Instance variables
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
 public void GoalTracker() {
  this.goalArray = new Goal[6];
  try{
  this.loadProgress();
  }catch(ioexption) //TODO FIX EXCEPTION HANDLING
  
  this.updateProgress();
  }
 
 }


 /***********Getters***********/

 public Goal getGoalArray(){
return this.goalArray;	 
 }

 /***************Setters****************************/



 /****************************methods *********************************/

 /*this method will load the file and store it's progress*/
 public void loadProgress() {
  try {
   // Input stream to read the file
   FileInputStream saveFile = new FileInputStream("saveFile.sav");

   //restores the variables stored in the object
   ObjectInputStream save = ObjectInputStream(saveFile);

   //cast to read the object
   this.steps = (Goal) save.readObject();
   
   save.close();
  }
  //TODO add exception in case of null file.
 }


/////////////////////////////////////////////////////////////////
 /*This method will save a user progress*/
 public void saveProgress() {

  try {
   // Opens a file to write to called saveObj
   FileOutputStream saveFile = new FileOutputStream("saveFile.sav");

   //Creates an ObjectOutputStream to put files into.
   ObjectOutputStream save = new ObjectOutputStream(saveFile);

   //saves all the data to the object
   save.writeObject(this.goalArray);

   //close the outputstream
   save.close();
  }
  //TODO add null exception handler

 }
///////////////////////////////////////////////////////////////////
//Verify if the goal was achieved 
public boolean isAchieved(int apiData, int goalData){
if(apiData >= goalData)
this.getGoalArray.setAcheived(true);
return true;
else
	return false;

}

/////////////////////////////////////////////////////////////////////
public void setGoal(int goal, String type){
Goal goalObj = new Goal();
goalObj.setTarget(goal); //this method sets the target for the Goal object
this.insertArray(type, goalObj); // this method will store the object in the appropriate depending on their type.
}

/////////////////////////////////////////////////////////////////////
/*Helper method*/
private insertArray(String type, Goal goal){	

switch(type.toLowerCase()){

case "steps":
this.goalArray[0] = goal;
break;

case "distance":
this.goalArray[1]= goal;
break;

case "caloriesburned":
this.goalArray[2]= goal;
break;

case "floorsclimbed":
this.goalArray[3]= goal;
break;

case "activeminutes":
this.goalArray[4]= goal;
break;

case "sedentaryminutes":
this.goalArray[5]=goal;
break;

default:
System.out.println("Please enter a valid type");
break;	
}
return;
}
/////////////////////////////////////////////////////


public getGoal(){
this.goalArray;
}



public void updateProgress(){
	
APIData source = new APIData(); // gather the data

int APIsteps = source.getSteps();
if(this.goalArray[0]) //steps goal
this.stepsProgress = (float)this.getGoalArray[0] / APIsteps *100;


int APIdistance = source.getDistance();
if(this.goalArray[1]) //distance goal
this.distanceProgress = (float)this.getGoalArray[1] / APIsteps *100;

int APIcalories = source.getCalories();
if(this.goalArray[2]) //calories goal
this.caloriesProgress = (float)this.getGoalArray[2] / APIcalories *100;

int APIfloors = source.floorsClimbed();
if(this.goalArray[3]) //floors goal
this.floorsClimbedProgress = (float)this.getGoalArray[3] / APIfloors *100;

int APIactiveMinutes = source.getActiveMinutes();
if(this.goalArray[4]) //activeMinutes goal
this.activeMinutesProgress = (float)this.getGoalArray[4] / APIactiveMinutes * 100;

int APIsedentaryMinutes = source.getSendentaryMinutes();
if(this.goalArray[5]) //sedentary minutes goal
this.sedentaryMinutesProgress = (float) this.getGoalArray[5] / APIsedentaryMinutes * 100;
	
	
}








}