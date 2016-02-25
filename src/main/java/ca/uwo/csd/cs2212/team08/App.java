package ca.uwo.csd.cs2212.team08;
import javax.swing.SwingUtilities;

public class App {

	public static void main (String args[]){
		APIData test = new APIData();
		test.refreshDashBoardData(0, 0, 0);
		System.out.println("Floors Climbed: " + test.getFloorsClimbed());
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
