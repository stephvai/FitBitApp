package team08_app;
import javax.swing.SwingUtilities;

public class App {

	public static void main (String args[]){
		 SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                   MainScreen window = new MainScreen();
                   window.setVisible(true);
             }
        }); }
	}

