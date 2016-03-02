package ca.uwo.csd.cs2212.team08;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {

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
				//JOptionPane.showMessageDialog(contentPane, "An error has occured connecting to fitbit servers, please try again later.");
			}
		}
		
		//pass in api data to mainscreen
		System.out.println(date);
		MainScreen window = new MainScreen(date, apiData);
		window.setVisible(true);

		/*
		 SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                   MainScreen window = new MainScreen();
                   window.setVisible(true);
             }
        }); }
		 */
	}
}
