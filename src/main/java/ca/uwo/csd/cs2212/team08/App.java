package ca.uwo.csd.cs2212.team08;
import javax.swing.SwingUtilities;

public class App {

	public static void main (String args[]){
		System.out.println("Hello World");
		APIData data = new APIData();
		data.refreshData(1, 1, 1);
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
