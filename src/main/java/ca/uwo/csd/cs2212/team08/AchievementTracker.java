package ca.uwo.csd.cs2212.team08;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AchievementTracker {

	private Achievement[] achievementArray;
	/* Constructor
	 * This method will create an Achievement array of 20 and load the progress of the users. 
	 */
	public AchievementTracker() throws ClassNotFoundException, IOException {
		        this.achievementArray = new Achievement[20];

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
	public void loadProgress() throws IOException, ClassNotFoundException {

		// Input stream to read the file
		FileInputStream	saveAchieve = new FileInputStream("Achieve.ini");


		//restores the variables stored in the object
		ObjectInputStream save = new ObjectInputStream(saveAchieve);

		//cast to read the object
		this.achievementArray = (Achievement[]) save.readObject();

		save.close();

		}

	public void updateProgress() {
		APIData source = new APIData();
		float APIsteps = source.getSteps();
		
	}
	
	public void isAchieved() {
		
	}
}
