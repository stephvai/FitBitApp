public class GoalTracker{

/*Instance variables */
private int steps;
private int distance;
private int caloriesBurned;
private int floorsClimbed;
private int activeMinutes;
private int sedentaryMinutes;

/*Getters*/

public int getSteps(){
	return this.steps;
}

public int getDistance(){
	return this.distance;
}

public int getcaloriesBurned(){
	return this.caloriesBurned;
}

public int getFloorsCLimbed(){
	return this.gloorsClimbed;
}

public int getActiveMinutes(){
	return this.activeMinutes;
}

public int getsedentaryMinutes(){
	return this.sedentaryMinutes;
}

/*Setters*/

public void setSteps(int steps){
	this.steps = steps;
}

public void setDistance(int distance){
	this.distance = distance;
}

public void setcaloriesBurned(int caloriesBurned){
	this.caloriesBurned = caloriesBurned;
}

public void setFloorsCLimbed(int floorsClimbed){
	this.floorsClimbed = floorsClimbed;
}

public void setActiveMinutes(int activeMinutes){
	this.activeMinutes = activeMinutes;
}

public void setsedentaryMinutes(int sedentaryMinutes){
	this.sedentaryMinutes = sedentaryMinutes;
}


}