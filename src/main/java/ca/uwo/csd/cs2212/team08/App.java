package ca.uwo.csd.cs2212.team08;
import javax.swing.SwingUtilities;

public class App {

	public static void main (String args[]){
		System.out.println("Hello World");
		APIData service = new APIData("2016-02-26");
		System.out.println("Daily Steps: " + service.getSteps());
		service.refreshDailyDashBoardData("2016-02-25");
		System.out.println("Daily Steps: " + service.getSteps());
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