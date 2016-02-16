package team08_app;
import javax.swing.JFrame;

public class MainScreen extends JFrame {
	    
	     public MainScreen() {
	          this.initUI();
	     }
	    
	     private void initUI () {
	          this.setTitle("team08-Fitbit"); //title
	          this.setSize(700, 700); //size- we can change this
	          this.setLocationRelativeTo(null); 
	          this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	     }
	}

