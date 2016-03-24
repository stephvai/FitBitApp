package ca.uwo.csd.cs2212.team08;

import javax.swing.*;
import java.util.Calendar;

/**
 * the main page to start the application
 */
public class App {

	/**
	 * a main class in charge of starting the application
	 * @param args a parameter that checks if it is in test mode or not
	 */
	public static void main (String args[]){
		//set the default date to the current date
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		//add an extra zero to the month if it is needed
		String monthString;
		if (month<10)
		{
			monthString = "0" + Integer.toString(month);
		}
		else
		{
			monthString = Integer.toString(month); 
		}
		//save the date to a string
		String date = Integer.toString(year) +  "-" + monthString + "-" + Integer.toString(day);

		APIData apiData;
		//if the user passes in test mode as a parameter
		if (args.length >= 1 && args[0].toLowerCase().equals("test")) {
			apiData = new APIDataTest();
			apiData.refreshDailyDashBoardData(date);
		}
		//otherwise use the regular mode
		else {
			apiData = new APIData();
			if (!apiData.refreshDailyDashBoardData(date)) {
				//DISPLAY ERROR CONNECTING TO FITBIT SERVERS
				JOptionPane.showMessageDialog(null, "An error has occured connecting to fitbit servers, please try again later.");
			}
		}
		
		//pass in api data to mainscreen
		MainScreen window = new MainScreen(date, apiData);
		window.setVisible(true);
	}
}
