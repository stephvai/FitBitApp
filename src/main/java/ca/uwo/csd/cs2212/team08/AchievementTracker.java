package ca.uwo.csd.cs2212.team08;

import java.io.*;

public class AchievementTracker {

	private float stepsProgress;
	private float distanceProgress;
	private float caloriesProgress;
	private float floorsProgress;
	private APIData source;
	private Achievement[] achievementArray;

	/* Constructor
	 * This method will create an Achievement array of 22 and load the progress of the users. 
	 */
	public AchievementTracker(APIData source) throws ClassNotFoundException, IOException {

		this.achievementArray = new Achievement[22];

		this.source = source;

		float startingSteps = 10000;
		float startingFloors = 100;
		float startingDistance = 10;
		float startingCalories = 2000;
		
		//Did user reach 10,000, 12,500, 15,000, 17,500, 20,000 steps in one day?
		for(int i=0;i<5;i++){
			Achievement achievement = new Achievement();
			achievement.setSteps(startingSteps);
			this.achievementArray[i]=achievement;
			startingSteps+=2500;
		}
		
		//Did user reach 100, 200, 300, 400, 500 floors in one day?
		for(int i=5;i<10;i++){
			Achievement achievement = new Achievement();
			achievement.setFloorsClimbed(startingFloors);
			this.achievementArray[i]=achievement;
			startingFloors+=100;
		}
		
		//Did user reach 10, 20, 30, 40, 50 miles in one day?
		for(int i=10;i<15;i++){
			Achievement achievement = new Achievement();
			achievement.setDistance(startingDistance);
			this.achievementArray[i]=achievement;
			startingDistance+=10;
		}
		
		//Did user burn 2000, 2250, 2500, 2750, 3000, 3250, 3500 calories in one day?
		for(int i=15;i<22;i++){
			Achievement achievement = new Achievement();
			achievement.setCalories(startingCalories);
			this.achievementArray[i]=achievement;
			startingCalories+=250;
		}
		
		this.loadProgress();
		this.updateProgress();
	}
/*
	public void setAchievement(float target, AchievementsEnum type) {
		Achievement achievementObj = new Achievement();

		if(type == AchievementsEnum.steps){ //sets the goal for the steps.
			achievementObj.setSteps(target);
			this.achievementArray[0] = achievementObj;
			return;
		}
		else if(type==AchievementsEnum.distance){
			achievementObj.setDistance(target);
			this.achievementArray[1] = achievementObj;
			return;
		}
		else if(type==AchievementsEnum.calorieBurned){
			achievementObj.setCalories(target);
			this.achievementArray[2] = achievementObj;
			return;
		}
		else if(type==AchievementsEnum.floorsClimbed){
			achievementObj.setFloorsClimbed(target);
			this.achievementArray[3] = achievementObj;
			return;
		}
	}
	*/
	/****************************methods
	 * @throws IOException
	 * @throws ClassNotFoundException *********************************/
	
	//this method will save a user's progress
	public void saveProgress() throws IOException {
		// Creates a file and writes it to Object called saveAchieve
        FileOutputStream saveAchieve;
		saveAchieve = new FileOutputStream("Achieve.ini");
	
        //Creates an ObjectOutputStream to put files into.
        ObjectOutputStream save = new ObjectOutputStream(saveAchieve);

        //saves data to this object
        save.writeObject(this.achievementArray);

        //close the outputstream
        save.close();
}
	//this method will load the Achieve.ini file and store the user's progress
	public void loadProgress()  {
		// Input stream to read the file
		FileInputStream	saveAchieve = null;
		try {
			saveAchieve = new FileInputStream("Achieve.ini");
			
			ObjectInputStream save = new ObjectInputStream(saveAchieve);

			//cast to read the object
			this.achievementArray = (Achievement[]) save.readObject();

			save.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
			return;
		}catch(IOException e){
			e.printStackTrace();
			return;
		}
		}

	public boolean isAchieved(int i){
		return this.achievementArray[i].getAchieved();
	}

	public void updateProgress() {

		this.source = new APIData();//Gather the API data.

		/*Check the steps progress of the achievements.*/
		float APIsteps = source.getSteps();
		for(int i=0;i<5;i++){
			this.stepsProgress = APIsteps/(float) this.achievementArray[i].getObjective()/APIsteps*100.0f;
			if (this.stepsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the floors progress of the achievements.*/
		float APIfloors = source.getFloorsClimbed();
		for(int i=5;i<10;i++){
			this.floorsProgress = APIfloors/(float) this.achievementArray[i].getObjective()/APIfloors*100.0f;
			if (this.floorsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the distance progress of the achievements.*/
		float APIdistance = source.getDistance();
		for(int i=10;i<15;i++){
			this.distanceProgress = APIdistance/(float) this.achievementArray[i].getObjective()/APIdistance*100.0f;
			if (this.distanceProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the calories progress of the achievements.*/
		float APIcalories = source.getCalories();
		for(int i=15;i<22;i++){
			this.caloriesProgress = (float) APIcalories/this.achievementArray[i].getObjective()/APIcalories*100.0f;
			if (this.caloriesProgress >= 100)
				this.achievementArray[i].setAchieved();
		}
	}
}
