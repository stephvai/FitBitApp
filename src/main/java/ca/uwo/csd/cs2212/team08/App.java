package ca.uwo.csd.cs2212.team08;
import javax.swing.SwingUtilities;

public class App {

	public static void main (String args[]){
		System.out.println("Hello World");
		MainScreen window = new MainScreen();
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