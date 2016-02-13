import java.io.*;

public class GoalTracker {

 /*Instance variables */

 private int steps;
 private int distance;
 private int caloriesBurned;
 private int floorsClimbed;
 private int activeMinutes;
 private int sedentaryMinutes;
 private Goal[] goalArray;


 /*constructor
 This method will create a Goal array of 10 and load the progress of the user.
 */
 public void GoalTracker() {
  this.goalArray = new Goal[10];
  loadProgress();
 }


 /***********Getters***********/

 public int getSteps() {
  return this.steps;
 }

 public int getDistance() {
  return this.distance;
 }

 public int getcaloriesBurned() {
  return this.caloriesBurned;
 }

 public int getFloorsCLimbed() {
  return this.gloorsClimbed;
 }

 public int getActiveMinutes() {
  return this.activeMinutes;
 }

 public int getsedentaryMinutes() {
  return this.sedentaryMinutes;
 }

 /***************Setters****************************/

 public void setSteps(int steps) {
  this.steps = steps;
 }

 public void setDistance(int distance) {
  this.distance = distance;
 }

 public void setcaloriesBurned(int caloriesBurned) {
  this.caloriesBurned = caloriesBurned;
 }

 public void setFloorsCLimbed(int floorsClimbed) {
  this.floorsClimbed = floorsClimbed;
 }

 public void setActiveMinutes(int activeMinutes) {
  this.activeMinutes = activeMinutes;
 }

 public void setsedentaryMinutes(int sedentaryMinutes) {
  this.sedentaryMinutes = sedentaryMinutes;
 }


 /****************************methods *********************************/

 /*this method will load the file and store it's progress*/
 public void loadProgress() {
  try {
   // Input stream to read the file
   FileInputStream saveFile = new FileInputStream("saveFile.sav");

   //restores the variables stored in the object
   ObjectInputStream save = ObjectInputStream(saveFile);

   //cast to read the object
   this.steps = (int) save.readObject();
   this.distance = (int) save.readObject();
   this.caloriesBurned = (int) save.readObject();
   this.floorsClimbed = (int) save.readObject();
   this.activeMinutes = (int) save.readObject();
   this.sedentaryMinutes = (int) save.readObject();

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
   save.writeObject(this.steps);
   save.writeObject(this.distance);
   save.writeObject(this.caloriesBurned);
   save.writeObject(this.floorsClimbed);
   save.writeObject(this.activeMinutes);
   save.writeObject(this.sedentaryMinutes);

   //close the outputstream
   save.close();
  }
  //TODO add null exception handler

 }
///////////////////////////////////////////////////////////////////
//Verify if the goal was achieved 
public static boolean isAchieved(int apiData, int goalData ){
if(apiData >= goalData)
return true;
else
	return false;

}

/////////////////////////////////////////////////////////////////////









}