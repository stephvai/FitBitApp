package ca.uwo.csd.cs2212.team08;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainScreen extends JFrame {
	
	private JPanel contentPane;
	    
	     public MainScreen() {
	          this.initUI();
	     }
	    
	     private void initUI () {
	    	 this.setTitle("team08-Fitbit"); //title
	    	 this.setSize(700, 700); //size- we can change this
	    	 this.setLocationRelativeTo(null); 
	    	 this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	    	 //created a steps panel that when clicked launches the steps page
	    	 DashBoardPanel pnlSteps = new DashBoardPanel(75, 200);
	    	 pnlSteps.setBackground(Color.BLACK);
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click here!
	    			 StepsPanel steps = new StepsPanel();
	    			 dispose();
	    		 }
	    	 });
	    	 contentPane.add(pnlSteps);
	     }
}

