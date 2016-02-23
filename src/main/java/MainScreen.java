package ca.uwo.csd.cs2212.team08;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

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
	    	 contentPane = new JPanel();
	    	 contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    	 setContentPane(contentPane);

	    	 //created a steps panel that when clicked launches the steps page
	    	 DashBoardPanel pnlSteps = new DashBoardPanel(75, 500);
	    	 pnlSteps.setBackground(Color.BLACK);
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click here!
	    			 StepsPanel steps = new StepsPanel();
	    			 steps.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 contentPane.add(pnlSteps);
	    	 
	    	 //add a label 
	 		JLabel lblTitle = new JLabel("Fit Bit");
			lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 27));
			lblTitle.setBounds(491, 21, 133, 70);
			contentPane.add(lblTitle);
	     }
}

