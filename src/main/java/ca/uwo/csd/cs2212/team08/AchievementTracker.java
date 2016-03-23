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
	 * This method will create an Achievement array of 21 and load the progress of the users.
	 * 1.10k steps in one day
	 * 2.15k steps in one day
	 * 3.20k steps in one day
	 * 4.50k steps in total
	 * 5.100k steps in total
	 * 6.250k steps in total
	 * 7.30 floors in one day
	 * 8.50 floors in one day
	 * 9.100 floors in one day
	 * 10.500 floors in total
	 * 11.1000 floors in total
	 * 12.2000 floors in total
	 * 13.10km in one day
	 * 14.15km in one day
	 * 15.20km in one day
	 * 16.200km in total
	 * 17.300km in total
	 * 18.500km in total
	 * 19.2000 calories in one day
	 * 20.3000 calories in one day
	 * 21.5000 calories in total
	 */
	public AchievementTracker(APIData source) throws ClassNotFoundException, IOException {

		this.achievementArray = new Achievement[21];
		
		for (int i = 0; i < achievementArray.length; i++) {
			Achievement achievement = new Achievement();
			this.achievementArray[i] = achievement;
		}
		
		this.source = source;

		float startingDaySteps = 10000;
		float startingTotalSteps = 50000;
		float startingDayFloors = 30;
		float startingDistance = 10;

		/*Set the achievements of 10k, 15k and 20k steps in a day.*/
		for(int i=0;i<3;i++){
			Achievement achievement = new Achievement();
			achievement.setSteps(startingDaySteps);
			this.achievementArray[i]=achievement;
			startingDaySteps+=5000;
		}

		/*Set the achievements of 50k, 100k, 250k steps in life-time total.*/
		for(int i=3;i<=5;i++){
			Achievement achievement = new Achievement();
			achievement.setSteps(startingTotalSteps);
			this.achievementArray[i]=achievement;
			startingTotalSteps+=50000;
		}

		this.achievementArray[5].setSteps(250000);

		/*Set the achievements of 30, 50, 100 floors in a day.*/
		for(int i=6;i<8;i++){
			Achievement achievement = new Achievement();
			achievement.setFloorsClimbed(startingDayFloors);
			this.achievementArray[i]=achievement;
			startingDistance+=20;
		}
		this.achievementArray[8].setFloorsClimbed(100);

		/*Set the achievements of 500, 1000, 2000 floors in life-time total.*/
		this.achievementArray[9].setFloorsClimbed(500);
		this.achievementArray[10].setFloorsClimbed(1000);
		this.achievementArray[11].setFloorsClimbed(2000);

		/*Set the achievements of 10km, 15km, 20km in a day.*/
		for(int i=12;i<15;i++){
			Achievement achievement = new Achievement();
			achievement.setDistance(startingDistance);
			this.achievementArray[i]=achievement;
			startingDistance+=5;
		}
		/*Set the achievements of 200km, 300km, 500km in life-time total.*/
		this.achievementArray[15].setDistance(200);
		this.achievementArray[16].setDistance(300);
		this.achievementArray[17].setDistance(500);

		/*Set the achievements of burning 2000, 3000 calories in a day.*/
		this.achievementArray[18].setCalories(2000);
		this.achievementArray[19].setCalories(3000);

		/*Set the achievements of burning 5000 calories in life-time total*/
		this.achievementArray[20].setCalories(5000);

		this.loadProgress();
		this.updateProgress();
	}

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
		}catch (FileNotFoundException e) {
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


		/*Check the steps progress of the daily achievements.*/
		float APIsteps = source.getSteps();
		for(int i=0;i<3;i++){
			this.stepsProgress = APIsteps/(float) this.achievementArray[i].getObjective()/APIsteps*100.0f;
			if (this.stepsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the steps progress of the life-time achievements.*/
		float APITotalSteps = source.getTotalSteps();
		for(int i=3;i<6;i++){
			this.stepsProgress = APITotalSteps/(float) this.achievementArray[i].getObjective()/APIsteps*100.0f;
			if (this.stepsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the floors progress of the daily achievements.*/
		float APIfloors = source.getFloorsClimbed();
		for(int i=6;i<9;i++){
			this.floorsProgress = APIfloors/(float) this.achievementArray[i].getObjective()/APIfloors*100.0f;
			if (this.floorsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}
		/*Check the floors progress of the life-time achievements.*/
		float APITotalFloors = source.getTotalFloors();
		for(int i=9;i<12;i++){
			this.floorsProgress = APITotalFloors/(float) this.achievementArray[i].getObjective()/APIfloors*100.0f;
			if (this.floorsProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the distance progress of the daily achievements.*/
		float APIdistance = source.getDistance();
		for(int i=12;i<15;i++){
			this.distanceProgress = APIdistance/(float) this.achievementArray[i].getObjective()/APIdistance*100.0f;
			if (this.distanceProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the distance progress of the life-time achievements.*/
		float APITotalDistance = source.getTotalDistance();
		for(int i=15;i<18;i++){
			this.distanceProgress = APITotalDistance/(float) this.achievementArray[i].getObjective()/APIdistance*100.0f;
			if (this.distanceProgress >= 100)
				this.achievementArray[i].setAchieved();
		}

		/*Check the calories progress of the achievements.*/
		float APIcalories = source.getCalories();
		for(int i=18;i<20;i++){
			this.caloriesProgress = (float) APIcalories/this.achievementArray[i].getObjective()/APIcalories*100.0f;
			if (this.caloriesProgress >= 100)
				this.achievementArray[i].setAchieved();
		}
	}
}
