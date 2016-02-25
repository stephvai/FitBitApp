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
import java.awt.Panel;

public class MainScreen extends JFrame {
	
	private JPanel contentPane;
	    
	     public MainScreen() {
	          this.initUI();
	     }
	    
	     private void initUI () {
	    	 this.setTitle("Team08 Fitbit");
	    	 this.setSize(1080, 920);
	    	 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	 contentPane = new JPanel();
	    	 contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    	 this.setContentPane(contentPane);
	    	 contentPane.setLayout(null);
	    	 
	    	 //create a dash board panel for the steps
	    	 DashBoardPanel pnlSteps = new DashBoardPanel(150, 150);
	    	 pnlSteps.setBackground(Color.blue);
	    	 pnlSteps.addMouseListener(new MouseAdapter() {
	    		 @Override
	    		 public void mouseClicked(MouseEvent arg0) {
	    			 //what to do on button click
	    			 StepsPanel steps = new StepsPanel();
	    			 steps.setVisible(true);
	    			 dispose();
	    		 }
	    	 });
	    	 pnlSteps.setLayout(null);
	    	 contentPane.add(pnlSteps);
	    	 
	    	 //create a label to display the title of the panel
	    	 JLabel lblTitle = new JLabel("Fit Bit");
	    	 lblTitle.setBounds(318, 10, 68, 33);
	    	 contentPane.add(lblTitle);

	     }
}

